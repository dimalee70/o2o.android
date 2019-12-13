package kz.dragau.larek.presentation.presenter.home

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.response.OrdersByOutletResponce
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.models.objects.TestEvent
import kz.dragau.larek.presentation.presenter.BasePresenter
import kz.dragau.larek.presentation.view.home.HomeMainView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class HomeMainPresenter(private var router: Router) : BasePresenter<HomeMainView>() {

    var liveOrderByOutletResponse = MutableLiveData<OrdersByOutletResponce>()

    var liveTestEventResponce = MutableLiveData<TestEvent>()

    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }
    fun getOrdersByOtlet(salesOuterId: String){
        disposables.add(
        client.getOrdersByOutlet(salesOuterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                        liveOrderByOutletResponse.value = result
                },
                {
                    error ->
                    run{
                        viewState!!.showError(error)
                        Timber.i(error.localizedMessage)
                    }
                }
            )
        )
    }
    fun openCustoms(){
        viewState!!.openCustomsScreen()
    }

    fun observeForOrderByOutletResponseBoundary(): MutableLiveData<OrdersByOutletResponce>{
        return liveOrderByOutletResponse
    }

    fun observeForTestEventResponseBoundaty(): MutableLiveData<TestEvent>{
        return liveTestEventResponce
    }
}
