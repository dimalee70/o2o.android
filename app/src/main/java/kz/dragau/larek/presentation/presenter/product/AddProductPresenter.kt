package kz.dragau.larek.presentation.presenter.product

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.presentation.view.product.AddProductView

@InjectViewState
class AddProductPresenter : MvpPresenter<AddProductView>() {
    private var disposable: Disposable? = null
    private var imageUrl: String = ""

    fun pickPhoto(){
        viewState?.pickPhoto()
    }

    fun uploadImage(image: Bitmap){
        viewState?.showLoadedImage(image)
    }
}
