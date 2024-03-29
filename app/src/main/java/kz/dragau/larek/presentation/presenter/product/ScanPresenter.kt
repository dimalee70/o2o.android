package kz.dragau.larek.presentation.presenter.product

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.journeyapps.barcodescanner.BarcodeResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.presentation.view.product.ScanView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ScanPresenter(private val router: Router
                    , private  var productRegisterViewModel: ProductRegisterViewModel
) : MvpPresenter<ScanView>() {
    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun goBack()
    {
//        println("Click IMage view")
//        router.navigateTo(Screens.ProductRegisterScreen())
        router.navigateTo(Screens.LoginScreen())
    }

    fun navigateToRegisterScreen(){
        router.navigateTo(Screens.StoreScreen())
    }
//
@SuppressLint("CheckResult")
fun  checkProduct(barcode: String){
        client.getProductByBarcode(barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                    run {
                        if(result.resultObject != null) {
                            productRegisterViewModel.productId = result.resultObject.productId
                            productRegisterViewModel.title = result.resultObject.name
                            productRegisterViewModel.produserName = result.resultObject.manufacturer
                            productRegisterViewModel.describe = result.resultObject.description
                            productRegisterViewModel.categoryName = result.resultObject.productCategoryId
                            productRegisterViewModel.barCode = result.resultObject.barCode
                            if (result.resultObject.productImageBase64.isNullOrEmpty())
                                productRegisterViewModel.isVisiblePhoto = false
                            productRegisterViewModel.imageUri = result.resultObject.productImageBase64
                            productRegisterViewModel.isVisiblePhoto = true
                            productRegisterViewModel.isEnable = false
                            viewState.showProductExistsDialog()
                            return@subscribe
                        }
                        productRegisterViewModel.isVisiblePhoto = false
                        productRegisterViewModel.isEnable = true
                        productRegisterViewModel.barCode = barcode
                        router.navigateTo(Screens.ProductScreen())

                    }
                },
                {
                    error ->
                    run {
                        productRegisterViewModel.isVisiblePhoto = false
                        productRegisterViewModel.isEnable = true
                        viewState.showError(error)
                    }
                }
            )
    }
}
