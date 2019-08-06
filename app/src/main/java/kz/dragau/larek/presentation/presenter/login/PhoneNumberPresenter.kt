package kz.dragau.larek.presentation.presenter.login

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.presentation.view.login.PhoneNumberView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PhoneNumberPresenter(private val router: Router) : MvpPresenter<PhoneNumberView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val userRequstModel = LoginRequestModel().apply{ mobilePhone = "+77055717177" }

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun getSmsCode()
    {
        disposable = client.getSmsCode(userRequstModel.mobilePhone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (result.result == true)
                    {
                        userRequstModel.smsCode = result.resultObject!!
                        router.navigateTo(Screens.SmsCodeScreen())
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (error is HttpException)
                    {
                        if (error.code() == 403)
                        {
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
}
