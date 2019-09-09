package kz.dragau.larek.presentation.presenter.store

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.presentation.presenter.map.SaleSelector.Listener
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import org.json.JSONObject
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import kotlin.math.sqrt

@InjectViewState
class RegisterStorePresenter(private val router: Router, private var saleSelector: SaleSelector) : MvpPresenter<RegisterStoreView>()
{
    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
//        saleSelector.listener = object : Listener {
//            override fun onChange(salesOuter: SalesOuter) {
//                updateSale()
//            }
//        }
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
        val gson = Gson()
//        val jsonObject = JSONObject(gson.toJson(saleSelector.salesOuter))
        val jsonObject = JSONObject("{\n" +
                "\t\"name\":\"Шалом\",\n" +
                "\t\"address\":\"Богенбай батыра 51\",\n" +
                "\t\"latitude\":43.254568,\n" +
                "\t\"longitude\":76.962351,\n" +
                "\t\"isAcceptOrders\":false\n" +
                "}")
        println("body")
        println(jsonObject.toString())
        client.registerStore(jsonObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                    run {

                    }
                },
                {
                    error ->
                    run {

                    }
                    if (error is HttpException) {
                        if (error.code() == 500) {
                        }
                    }
                }
            )

//        val jsonObject =
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
