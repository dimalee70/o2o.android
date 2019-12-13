package kz.dragau.larek.presentation.presenter.store

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.presentation.view.store.ShowImageFragmentView
import ru.terrakok.cicerone.Router

@InjectViewState
class ShowImageFragmentPressenter(private val router: Router, private val images: ArrayList<Uri>): MvpPresenter<ShowImageFragmentView>(){
    init {
        App.appComponent.inject(this)
    }

    fun addPhoto(){
        viewState.showPictureDialog()
    }
}