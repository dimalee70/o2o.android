package kz.dragau.larek.presentation.presenter.product

import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.zxing.integration.android.IntentIntegrator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.api.response.ProductCategoriesResponce
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

    fun registerStore(){
        println(productRegisterViewModel)
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

