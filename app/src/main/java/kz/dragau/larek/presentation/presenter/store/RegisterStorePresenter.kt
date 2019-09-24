package kz.dragau.larek.presentation.presenter.store

import android.annotation.SuppressLint
import android.location.Geocoder
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.RegisterStoreRequestModel
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import kz.dragau.larek.ui.rule.NotEmptyRule
import kz.dragau.larek.ui.rule.NotNullRule
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import ru.whalemare.rxvalidator.Validator

@InjectViewState
class RegisterStorePresenter(private val router: Router, private var saleSelector: SaleSelector, private var imageList: ArrayList<Uri> ) : MvpPresenter<RegisterStoreView>()
{
    @Inject
    lateinit var client: ApiManager

    var registerStoreRequestModel = RegisterStoreRequestModel()

    init {
        App.appComponent.inject(this)
//        saleSelector.listener = object : Listener {
//            override fun onChange(salesOuter: SalesOuter) {
//                updateSale()
//            }
//        }
    }

    val validator = Validator().apply {
        add(NotNullRule())
        add(NotEmptyRule())
    }

    private fun onNameTextChanges(text: String){
        validator.validate(text,
            onSuccess = {
                viewState?.onNameValid()
            },
            onError = {
                viewState?.onNameInvalid(it)
            })
    }
    private fun updateSale() {
        viewState.showSale(saleSelector.salesOuter!!)
//        saleSelector.salesOuter?.let { viewState.showSale(it) }
    }

    fun openMap(){
        router.navigateTo(Screens.LocationMapScreen())
    }

//    fun addPhoto(){
//        viewState.showPictureDialog()
//    }

    override fun onDestroy() {
        saleSelector.listener = null
        super.onDestroy()
    }

    fun changeSwitch(){
        viewState.changeSwitch()
    }

    fun registerStore(){

//        viewState?.initSalesOutlet()
        viewState?.initGeocoder()

//        val gson = Gson().newBuilder().registerTypeAdapter(JSONObject::class.java)
//        val jsonObject = JSONObject(gson.toJson(saleSelector.salesOuter))
//        val salesOuterAnotation = SalesOuter.ana
//        var jsonObject: JsonObject = JsonObject()
//        jsonObject.addProperty(saleSelector.salesOuter.name, )
//        jsonObject.addProperty("address","Богенбай батыра 51" )
//        jsonObject.addProperty("latitude", 43.254568 )
//        jsonObject.addProperty("longitude",76.962351 )
//        jsonObject.addProperty("isAcceptOrders",false )
//        val jsonObject = JSONObject("{\n" +
//                "\t\"name\":\"Шалом\",\n" +
//                "\t\"address\":\"Богенбай батыра 51\",\n" +
//                "\t\"latitude\":43.254568,\n" +
//                "\t\"longitude\":76.962351,\n" +
//                "\t\"isAcceptOrders\":false\n" +
//                "}")
//        println("body")
//        println(jsonObject.toString())


    }


    fun registerToServer(){

        client.registerStore(saleSelector.salesOuter!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result ->
                    run {
                        println(result)
                    }
                },
                {

                        error ->
                    run {
                        viewState.showError(App.appComponent.context().getString(
                            R.string.default_error_message), -1)
                    }
                    if (error is HttpException) {
                        if (error.code() == 500) {
                        }

                        viewState.showError(App.appComponent.context().getString(
                            R.string.default_error_message), -1)
                    }
                }
            )
    }
    //    fun getTokenResultApi(token: String){
//        val jsonObject: JsonObject = JsonObject()
//        jsonObject.addProperty(Constants.verificationCode,token )
//        client.getToken(jsonObject)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                        result ->
//                    run{
//                        viewState?.hideProgress()
//                    }
//
//                    saveToDb(result)
//
//                },
//                {
//                        error ->
//                    run {
//                        viewState?.hideProgress()
//                    }
//                    if (error is HttpException) {
//                        if (error.code() == 500) {
//                        }
//                    }
//
//                }
//            )
//    }
}
