package kz.dragau.larek.presentation.presenter.login

import android.content.SharedPreferences
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
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.presentation.view.login.PhoneNumberView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import rxfirebase2.auth.RxFirebaseAuth


@InjectViewState
class PhoneNumberPresenter(private val router: Router) : MvpPresenter<PhoneNumberView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val userRequstModel = LoginRequestModel()

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    var mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
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
                    }
                )
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked if an invalid request for verification is made,
            // for instance if the the phone number format is invalid.
            Timber.i("onVerificationFailed")

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                viewState?.hideProgress()
                viewState?.showError(null, R.string.invalid_phone_number)
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been
                // exceeded
                viewState?.hideProgress()
                viewState?.showError(null, R.string.quota_exceeded)
            }

        }
        override fun onCodeSent(verificationId: String?,
                                token: PhoneAuthProvider.ForceResendingToken?) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Timber.i("onCodeSent: $verificationId")
            // Save verification ID and resending token so we can use them later
            //mVerificationId = verificationId
            //mResendToken = token
        }
    }

    fun getToken(firebaseToken: String?)
    {

        if (firebaseToken == null)
        {
            viewState.hideProgress()
            return
        }

        viewState.hideProgress()
        router.navigateTo(Screens.LocationMapScreen())
    }


    fun getSmsCode()
    {
        viewState?.hideKeyboard()
        viewState?.showProgress()


        userRequstModel.mobilePhone?.let {
            viewState?.verifyPhoneNumber(it)
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
}
