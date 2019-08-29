package kz.dragau.larek.presentation.presenter.map

import kz.dragau.larek.models.objects.SalesOuter

class SaleSelector{
    var salesOuter: SalesOuter? = null
    var listener: Listener? = null

    public interface Listener{
        fun onChange(salesOuter: SalesOuter)
    }
}