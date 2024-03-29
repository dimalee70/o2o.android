package kz.dragau.larek.presentation.view.store

import android.content.Context
import android.util.DisplayMetrics
import com.arellomobile.mvp.MvpView
import com.suke.widget.SwitchButton
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.models.objects.SalesOutletResult

interface RegisterStoreView : MvpView, SwitchButton.OnCheckedChangeListener {
    fun showSale(salesOuter: SalesOuter)
    fun showPictureDialog()
//    fun choseFromGallery()
//    fun takePhotoFromCamera()
    fun changeSwitch()
    fun onNameValid()
    fun onNameInvalid(errorMessage: String)
//    fun initSalesOutlet()
    fun checkAddress()
    fun showError(message: String?, codeError: Int)
    fun initGeocoder()
}
