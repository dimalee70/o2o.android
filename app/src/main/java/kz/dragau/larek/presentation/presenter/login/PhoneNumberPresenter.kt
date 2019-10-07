package kz.dragau.larek.presentation.presenter.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.databinding.ObservableBoolean
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.presentation.view.login.PhoneNumberView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kz.dragau.larek.*
import kz.dragau.larek.api.TokenInterceptor
import rxfirebase2.auth.RxFirebaseAuth
import kz.dragau.larek.api.response.TokenResponse
import kz.dragau.larek.models.db.Converters.Companion.toOffsetDateTime
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.models.objects.User
import kz.dragau.larek.models.shared.DataHolder
import org.joda.time.DateTime
import java.util.*


@InjectViewState
class PhoneNumberPresenter(private val router: Router, smsSent: Boolean) : MvpPresenter<PhoneNumberView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var tokenInterceptor: TokenInterceptor

    val userRequstModel = LoginRequestModel()

    private var disposable: Disposable? = null

    private var myCode: String? = null

    var isSmsSent = ObservableBoolean()

    init {
        App.appComponent.inject(this)
        isSmsSent.set(smsSent)
    }

    private fun saveToDb(tokenResponse: TokenResponse)
    {
        var user: User = User(tokenResponse.resultObject?.systemUserId!!,
            userRequstModel.mobilePhone,
            tokenResponse.resultObject?.token,
            toOffsetDateTime(tokenResponse.resultObject?.expireDate))


        userDao.insert(
            user
        )

        DataHolder.userId = user.id
    }

    var verifId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var timer: Timer? = null
    val delay: Long = 0
    val period: Long = 500

    private fun signInFirebase(credential: PhoneAuthCredential)
    {
        disposable = RxFirebaseAuth
            .signInWithCredential(FirebaseAuth.getInstance(), credential)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it!!.getIdToken(true)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                getToken(task.result?.token)
                                stopTimedUpdate()
                                    //router.navigateTo(Screens.SmsCodeScreen())
                            } else {
                                if (task.exception?.message != null) {
                                    viewState?.hideProgress()
                                    viewState?.showError(task.exception!!.message!!, -1)
                                }
                            }
                        }
                },
                {
                    viewState?.hideProgress()
                    viewState?.showError(it)
                    it.printStackTrace()
                }
            )
    }

    private fun checkCode(code: String)
    {
        if (verifId == null)
        {
            viewState?.hideProgress()
            viewState?.showError("Проверка номера телефона не была инициирована", -1)
            return
        }

        if (userRequstModel.smsCode.isEmpty())
        {
            viewState?.hideProgress()
            viewState?.showError("Не введен код из SMS", -1)
            return
        }


        val credential = PhoneAuthProvider.getCredential(verifId!!, code)
        signInFirebase(credential)
    }


    var mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code: String? = credential.smsCode
            if (userRequstModel.smsCode.isNotEmpty()) {
                checkCode(code!!)
                stopTimedUpdate()
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked if an invalid request for verification is made,
            // for instance if the the phone number format is invalid.
            Timber.i("onVerificationFailed")

            viewState?.hideProgress()
            isSmsSent.set(false)
            stopTimedUpdate()

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                viewState?.showError(null, R.string.invalid_phone_number)
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been
                // exceeded
                viewState?.showError(null, R.string.quota_exceeded)
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Timber.i("onCodeSent: $verificationId")
            // Save verification ID and resending token so we can use them later
            verifId = verificationId
            resendToken = token
            userRequstModel.codeExpiryDate =
                DateTime.now().plusSeconds(Constants.smsVerificationDelay.toInt()).toDate()
            isSmsSent.set(true)
            startTimedUpdate()
            viewState.hideProgress()
        }
    }

    fun getToken(firebaseToken: String?)
    {

        if (firebaseToken == null)
        {
            viewState.hideProgress()
            return
        }
        getTokenResultApi(firebaseToken)
        //router.navigateTo(Screens.LocationMapScreen())
    }

    @SuppressLint("CheckResult")
    fun getTokenResultApi(token: String){
        val jsonObject: JsonObject = JsonObject()
        jsonObject.addProperty(Constants.verificationCode,token )
        client.getToken(jsonObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                        run{
                            viewState?.hideProgress()
                        }

                        saveToDb(result)
                        tokenInterceptor.token = "Bearer " + result.resultObject!!.token

                },
                {
                    error ->
                    run {
                        viewState?.hideProgress()
                    }
                    if (error is HttpException) {
                        if (error.code() == 500) {
                        }
                    }

                }
            )
    }

    fun getSmsCode()
    {

        viewState?.hideKeyboard()
        viewState?.showProgress()

        if (isSmsSent.get())
        {

            checkCode(userRequstModel.smsCode)
        }
        else {
            userRequstModel.mobilePhone?.let {
                viewState?.verifyPhoneNumber(it)
                //viewState?.hideProgress()
                //router.navigateTo(Screens.SmsCodeScreen())
            }
        }

        /*disposable = userRequstModel.mobilePhone?.let {
        client.getSmsCode(it)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {

                        viewState?.hideProgress()
                    }

                    if (result.result == true) {
                        userRequstModel.smsCode = result.resultObject!!
                        router.navigateTo(Screens.ConfirmCodeScreen(userRequstModel))

                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                        if (error is HttpException) {
                            if (error.code() == 403) {
                                sharedPreferences.edit().clear().apply()
                                //viewState?.showLogin()
                                return@subscribe
                            }
                        }

                        run {
                            viewState?.showError(error)
                        }
                    }
                )
        }
        */
    }

    fun startTimedUpdate()
    {
        if (timer != null)
        {
            timer?.cancel()
            timer = null
        }

        timer =  Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (userRequstModel.codeExpiryDate == null)
                {
                    return
                }

                if (DateTime.now().toDate() > userRequstModel.codeExpiryDate)
                {
                    userRequstModel.codeExpiryDate = null
                    //сделать действие по повторной отправке
                    stopTimedUpdate()
                    return
                }

                userRequstModel.notifyChange()
            }
        }, delay, period)
    }

    fun stopTimedUpdate()
    {
        timer?.cancel()
        timer = null
    }

    fun startCheck(text: String?)
    {
        viewState?.clearFocus()
        getSmsCode()
    }

    val startCheck: Function1<String, Unit> = this::startCheck
}
