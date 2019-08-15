package kz.dragau.larek.presentation.presenter.product

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.presentation.view.product.ProductView
import javax.inject.Inject

@InjectViewState
class ProductPresenter : MvpPresenter<ProductView>() {

}
