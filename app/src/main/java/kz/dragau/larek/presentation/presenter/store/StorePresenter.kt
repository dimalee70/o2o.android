package kz.dragau.larek.presentation.presenter.store

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.presentation.view.store.StoreView
import ru.terrakok.cicerone.Router

@InjectViewState
class StorePresenter(private val router: Router, private val imageList: Images) : MvpPresenter<StoreView>() {
    init {
        App.appComponent.inject(this)

    }

//    fun changeImage(uri: Uri){
//        saleSelector.imageSelector = uri.toString()
//    }

    fun addImage(uri: Uri) {
        imageList.images!!.add(uri)
    }
}
