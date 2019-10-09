package kz.dragau.larek.presentation.presenter.home

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.presentation.view.home.HomeMainView

@InjectViewState
class HomeMainPresenter : MvpPresenter<HomeMainView>() {
    fun openCustoms(){
        viewState.openCustomsScreen()
    }
}
