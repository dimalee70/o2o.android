package kz.dragau.larek.presentation.presenter.product

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.presentation.view.product.ProductRegisterView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProductRegisterPresenter(private var router: Router): MvpPresenter<ProductRegisterView>() {

    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun makePhoto() {

        Timber.i("Click Photo")
//        viewState?.hideKeyboard()
//        viewState?.showProgress()
//        println(userRequestModel!!.smsCode)
//        println(confirmRequestModel.confirmCode)
//        if (userRequestModel.smsCode.equals(confirmRequestModel.confirmCode)) {
//            println("Success")
//            viewState?.hideProgress()
//            router.newRootScreen(Screens.RegistrationScreen(userRequestModel))
//        } else {
//            viewState?.hideProgress()
//
//
//        }
    }
}

