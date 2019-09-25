package kz.dragau.larek.presentation.presenter.store

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.presentation.view.store.ImageViewPagerView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ImageViewPagerPresenter(private var router: Router) : MvpPresenter<ImageViewPagerView>() {
    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    fun showPictureDialog(){
        viewState?.showPictureDialog()
    }

    fun showConfirmDialog(){
        viewState?.showConfirm()
    }
}
