package kz.dragau.larek.presentation.presenter.store

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.presentation.view.store.ShowImageFragmentView
import ru.terrakok.cicerone.Router

@InjectViewState
class ShowImageFragmentPressenter(private val router: Router, private val images: Array<String>): MvpPresenter<ShowImageFragmentView>(){
    init {
        App.appComponent.inject(this)
    }

    fun addPhoto(){
        viewState.showPictureDialog()
    }
}