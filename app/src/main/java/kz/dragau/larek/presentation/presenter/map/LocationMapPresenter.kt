package kz.dragau.larek.presentation.presenter.map

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.models.objects.Boundaries
import kz.dragau.larek.models.objects.Points
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.BasePresenter
import kz.dragau.larek.presentation.view.map.LocationMapView
import org.jetbrains.annotations.Async
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LocationMapPresenter (private val router: Router, private val saleSelector: SaleSelector) : BasePresenter<LocationMapView>() {

    var liveSalesOutletResponse = MutableLiveData<SalesOutletResponse>()
    var liveSalesOuterResponseBoundary = MutableLiveData<SalesOutletResponse>()
    var liveCancellSearchButton = MutableLiveData<Boolean>()
    var liveSubmitButton = MutableLiveData<Boolean>()
    var isClickedMarker: Boolean? = false
    var salesOuter: SalesOuter? = null
    var isSubmitButton: Boolean = false

    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var disposable: Disposable? = null

    private val mapTypes = intArrayOf(GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_NORMAL)
    private var mapType = GoogleMap.MAP_TYPE_NORMAL

    init {
        App.appComponent.inject(this)

    }

    fun getSaleOutlet(){
        saleSelector.salesOuter = salesOuter
        router.exit()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun toggleMap()
    {
        mapType = mapTypes.first{ it != mapType}
        viewState.changeMapType(mapType)
    }

    fun cancelSearch(){
        liveCancellSearchButton.value = true
    }

    fun observeForCancellSearchButton(): MutableLiveData<Boolean>{
        return liveCancellSearchButton
    }

    fun setObserveForSubmitButton(visibility: Boolean){
        liveSubmitButton.value = visibility
    }

    fun setObserveForCancellSearchButton(enable: Boolean){
        liveCancellSearchButton.value = enable
    }

    fun setUpMap()
    {
        viewState?.setUpMap(mapType)
        liveCancellSearchButton.value = true
        liveSubmitButton.value = false
    }

    fun setSalesOutler(name: String?, legacyNmae: String?, address: String?)
    {
        salesOuter = SalesOuter(name, legacyNmae, address)
    }

    @SuppressLint("CheckResult")
    fun getSalesOutlenByName(name: String){

        disposables.add(
        client.getSalesOuterByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                    run {
                        liveSalesOutletResponse.value = result


                    }
                },
                {
                    error ->
                    run {

                        Timber.i(error.localizedMessage)
                    }
                }
            )
        )
    }

    fun getSalesOutlenByBoundary(boundaries: Boundaries){

        val jsonArray: JsonArray = JsonArray()

        if(boundaries.points != null && boundaries.points.size == 5) {
            boundaries!!.points!!.forEach {
                var jsonObject = JsonObject()
                jsonObject.addProperty("x", it.x)
                jsonObject.addProperty("y", it.y)
                jsonArray.add(jsonObject)
            }
        }
//        jsonArray.add(boundaries.points[0])
//        jsonArray.add(boundaries.points[0])
//        jsonArray.add(boundaries.points[0])
//        jsonObject.addProperty(Constants.verificationCode,token )

        disposables.add(
            client.getSalesOuterByBoundary(jsonArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        result ->
                        run {
                            liveSalesOuterResponseBoundary.value = result
                        }
                    },
                    {
                        error ->
                        run {
                            Timber.i(error.localizedMessage)
                        }
                    }
                )
        )
    }

    fun createBoundaries(p1: Points, p2: Points, p3: Points, p4: Points){
        getSalesOutlenByBoundary(Boundaries(mutableListOf(p1,p2,p3,p4,p1)))
    }

    fun observeForSalesOutletResponseBoundary(): MutableLiveData<SalesOutletResponse>{
        return liveSalesOuterResponseBoundary
    }

    fun observeForSalesOutletResponse(): MutableLiveData<SalesOutletResponse> {
        return liveSalesOutletResponse
    }

    fun obseverForSubmitButton(): MutableLiveData<Boolean>{
        return  liveSubmitButton
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