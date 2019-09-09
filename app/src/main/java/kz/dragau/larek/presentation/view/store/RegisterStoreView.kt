package kz.dragau.larek.presentation.view.store

import android.util.DisplayMetrics
import com.arellomobile.mvp.MvpView
import com.suke.widget.SwitchButton
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.models.objects.SalesOutletResult

interface RegisterStoreView : MvpView, SwitchButton.OnCheckedChangeListener {
    fun showSale(salesOuter: SalesOutletResult)
    fun showPictureDialog()
//    fun choseFromGallery()
//    fun takePhotoFromCamera()
    fun changeSwitch()
}
