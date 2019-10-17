package kz.dragau.larek.presentation.presenter.product

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.JsonObject
import com.google.zxing.integration.android.IntentIntegrator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.api.response.ProductCategoriesResponce
import kz.dragau.larek.extensions.encodeImage
import kz.dragau.larek.presentation.presenter.BasePresenter
import kz.dragau.larek.presentation.view.product.ProductRegisterView
import kz.dragau.larek.ui.activity.product.ScanActivity
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProductRegisterPresenter(private var router: Router, private var productRegisterViewModel: ProductRegisterViewModel): BasePresenter<ProductRegisterView>() {

    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var liveProductCategoriesResponse = MutableLiveData<ProductCategoriesResponce>()

    init {
        App.appComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun registerStore(){
        viewState.showLoading()

        if (productRegisterViewModel.productId != null){
            val body: JsonObject = JsonObject()
            body.addProperty("productId", productRegisterViewModel.productId)
            body.addProperty("productCategoryId", productRegisterViewModel.categoryName)
            body.addProperty("productImageBase64", productRegisterViewModel.imageUri)
            body.addProperty("name", productRegisterViewModel.title)
            body.addProperty("barCode", productRegisterViewModel.barCode)
            body.addProperty("manufacturer", productRegisterViewModel.produserName)
            body.addProperty("description", productRegisterViewModel.describe)

            client.updateProduct(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        run {

                            viewState.hideLoading()
                            router.exit()
                        }
                    },
                    { error ->
                        run {
                            viewState.hideLoading()
                            viewState.showError(error)
                        }
                    }
                )
        }
        else {
            val body: JsonObject = JsonObject()
            body.addProperty("productCategoryId", productRegisterViewModel.categoryName)
//            println("categoryId")
//            println(productRegisterViewModel.categoryName)
            body.addProperty("productImageBase64", productRegisterViewModel.imageUri)
            body.addProperty("name", productRegisterViewModel.title)
            body.addProperty("barCode", productRegisterViewModel.barCode)
            body.addProperty("manufacturer", productRegisterViewModel.produserName)
            body.addProperty("description", productRegisterViewModel.describe)

            client.createProduct(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        run {

                            viewState.hideLoading()
                            router.exit()
                        }
                    },
                    { error ->
                        run {
                            viewState.hideLoading()
                            viewState.showError(error)
                        }
                    }
                )
        }
    }

    fun makePhoto() {

        viewState?.showPictureDialog()

//        var integration: IntentIntegrator = IntentIntegrator()


//        router.navigateTo(Screens.ScanScreen())
//        Timber.i("Click Photo")



//        viewState?.hideKeyboard()
//        viewState?.showProgress()
//        println(userRequestModel!!.smsCode)
//        println(confirmRequestModel.confirmCode)
//        if (userRequestModel.smsCode.equals(confirmRequestModel.confirmCode)) {
//            println("Success")
//            viewState?.hideProgress()
//            router.newRootScreen(Screens.RegistrationScreen(userRequestModel))
//        } else {
//            viewState?.hideProgress()
//
//
//        }
    }

    fun getProductCategoris(){
        disposables.add(
            client.getProductategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        result ->
                        liveProductCategoriesResponse.value = result
                    },
                    {
                        error ->
                        run{
                            viewState.showError(error)
                        }
                    }
                )
        )
    }

    fun observeForProductCategoriesResponseBoundary(): MutableLiveData<ProductCategoriesResponce>{
        return liveProductCategoriesResponse
    }


}

