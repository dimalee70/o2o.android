package kz.dragau.larek.presentation.presenter.product

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.presentation.view.product.ProductView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProductPresenter(private  var router: Router, private var productRegisterViewModel: ProductRegisterViewModel) : MvpPresenter<ProductView>() {

    fun changeImage(uri: String){
        productRegisterViewModel.imageUri = uri
        productRegisterViewModel.isVisiblePhoto = true
    }
}
