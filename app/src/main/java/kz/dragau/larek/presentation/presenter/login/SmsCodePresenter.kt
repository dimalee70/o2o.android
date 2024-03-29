package kz.dragau.larek.presentation.presenter.login

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.presentation.view.login.SmsCodeView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SmsCodePresenter(private val router: Router, private val userRequestModel: LoginRequestModel? ) : MvpPresenter<SmsCodeView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun onBackPressed() {
        router.exit()
    }

    fun validateCode()
    {
        router.navigateTo(Screens.LocationMapScreen())
    }
}
