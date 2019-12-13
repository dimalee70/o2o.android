package kz.dragau.larek.presentation.presenter.store

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.presentation.view.store.ShowImageView
import ru.terrakok.cicerone.Router

@InjectViewState
class ShowImagePresenter(private var router: Router, private var imageList: Images) : MvpPresenter<ShowImageView>() {
    init {
        App.appComponent.inject(this)
    }

    fun goBack(){
        router.exit()
    }

    fun addImage(uri: Uri) {
        imageList.images!!.add(uri)
    }
}
