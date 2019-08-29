package kz.dragau.larek.presentation.view.map

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor



interface LocationMapView : MvpView {
    fun setUpMap(mapType: Int)
    fun changeMapType(mapType: Int)

}
