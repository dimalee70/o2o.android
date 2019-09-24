package kz.dragau.larek.ui.rule

import android.content.Context
import android.location.Geocoder
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import ru.whalemare.rxvalidator.ValidateRule
import javax.inject.Inject


class AddressRule(private var context: Context, private var saleSelector: SaleSelector): ValidateRule {

    override fun errorMessage(): String = "Not correct Address"

    override fun validate(data: String?): Boolean {
        try {

            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocationName(data, 1)
            if (addresses.size > 0){
                saleSelector.salesOuter = SalesOuter(null, null, null, null, true)
                saleSelector.salesOuter!!.latitude = addresses.get(0).latitude
                saleSelector.salesOuter!!.longitude = addresses.get(0).longitude
                saleSelector.salesOuter!!.address = data
            }
            println(saleSelector)
            println()
            return saleSelector.salesOuter!!.latitude != null && saleSelector.salesOuter!!.longitude != null
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }
}