package kz.dragau.larek.presentation.view.map

import com.arellomobile.mvp.MvpView

interface LocationMapView : MvpView {
    fun setUpMap(mapType: Int)
    fun changeMapType(mapType: Int)
}
