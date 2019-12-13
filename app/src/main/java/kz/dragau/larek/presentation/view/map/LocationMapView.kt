package kz.dragau.larek.presentation.view.map

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.maps.android.clustering.ClusterManager
import kz.dragau.larek.models.objects.SalesOutletResult
import kz.dragau.larek.presentation.BaseView


interface LocationMapView : MvpView, BaseView {
    fun setUpMap(mapType: Int)
    fun changeMapType(mapType: Int)
    fun readItems()
    fun getSalesOutletBoundaries()
    fun addToPo()
    fun drawMarkers()
    fun goToMyLocation()

}
