package kz.dragau.larek.presentation.presenter.home

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.presentation.view.home.HomeView
import ru.terrakok.cicerone.Router

@InjectViewState
class HomePresenter(private var router: Router) : MvpPresenter<HomeView>() {

}
