package kz.dragau.larek.presentation.presenter.product

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.presentation.view.product.ScanView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ScanPresenter(private val router: Router) : MvpPresenter<ScanView>() {
    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun goBack()
    {
        println("Click IMage view")
//        router.navigateTo(Screens.ProductRegisterScreen())
        router.navigateTo(Screens.LoginScreen())
    }

    fun navigateToRegisterScreen(){
        router.navigateTo(Screens.ProductRegisterScreen())
    }
}
