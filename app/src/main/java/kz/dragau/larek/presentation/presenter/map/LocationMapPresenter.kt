package kz.dragau.larek.presentation.presenter.map

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.maps.GoogleMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.presentation.presenter.BasePresenter
import kz.dragau.larek.presentation.view.map.LocationMapView
import org.jetbrains.annotations.Async
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LocationMapPresenter (private val router: Router) : BasePresenter<LocationMapView>() {

    var liveSalesOutletResponse = MutableLiveData<SalesOutletResponse>()

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

    fun onBackPressed() {
        router.exit()
    }

    fun toggleMap()
    {
        mapType = mapTypes.first{ it != mapType}
        viewState.changeMapType(mapType)
    }

    fun setUpMap()
    {
        viewState?.setUpMap(mapType)
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
                        println("Execute")


                    }
                },
                {
                    error ->
                    run {

                        Timber.i("Error")
                        error.printStackTrace()
                    }
                }
            )
        )
    }

    fun observeForSalesOutletResponse(): MutableLiveData<SalesOutletResponse> {
        return liveSalesOutletResponse
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