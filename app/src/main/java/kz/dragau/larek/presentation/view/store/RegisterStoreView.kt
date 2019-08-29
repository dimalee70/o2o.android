package kz.dragau.larek.presentation.view.store

import com.arellomobile.mvp.MvpView
import kz.dragau.larek.models.objects.SalesOuter

interface RegisterStoreView : MvpView {
    fun showSale(salesOuter: SalesOuter)
}
