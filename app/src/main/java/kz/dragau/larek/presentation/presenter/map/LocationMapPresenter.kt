package kz.dragau.larek.presentation.presenter.map

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.presentation.view.map.LocationMapView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LocationMapPresenter (private val router: Router) : MvpPresenter<LocationMapView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun onBackPressed() {
        router.exit()
    }
}