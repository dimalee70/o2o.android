package kz.dragau.larek.presentation.presenter.map

import android.net.Uri
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.models.objects.SalesOutletResult

class SaleSelector{
    var salesOuter: SalesOuter? = null
    var imageSelector: String? = null
    var listener: Listener? = null

    public interface Listener{
        fun onChange(salesOuter: SalesOutletResult)
    }
}