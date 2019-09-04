package kz.dragau.larek.presentation.presenter.store

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.presentation.presenter.map.SaleSelector.Listener
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import ru.terrakok.cicerone.Router
import kotlin.math.sqrt

@InjectViewState
class RegisterStorePresenter(private val router: Router, private var saleSelector: SaleSelector) : MvpPresenter<RegisterStoreView>()
{
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

    fun addPhoto(){
        println("Add PHOTO")
    }

    override fun onDestroy() {
        saleSelector.listener = null
        super.onDestroy()
    }
}
