package kz.dragau.larek.presentation.presenter.crop

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.presentation.view.crop.CropView

@InjectViewState
class CropPresenter(var uri: Uri) : MvpPresenter<CropView>() {
    var rotation = 0

    fun cancel()
    {
        viewState?.close()
    }

    fun proceed()
    {
        viewState?.cropImage()
    }

    fun rotate()
    {
        rotation -= 90
        viewState?.rotate(rotation)
    }
}
