package kz.dragau.larek.presentation.presenter.customs

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.presentation.view.customs.CustomsView
import ru.terrakok.cicerone.Router

@InjectViewState
class CustomsPresenter(private var router: Router) : MvpPresenter<CustomsView>() {

}
