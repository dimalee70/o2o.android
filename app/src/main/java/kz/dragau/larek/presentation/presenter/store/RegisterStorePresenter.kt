package kz.dragau.larek.presentation.presenter.store

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import ru.terrakok.cicerone.Router

@InjectViewState
class RegisterStorePresenter(private val router: Router) : MvpPresenter<RegisterStoreView>()
{
    init {
        App.appComponent.inject(this)
    }

    fun openMap(){
        router.navigateTo(Screens.LocationMapScreen())
    }
}
