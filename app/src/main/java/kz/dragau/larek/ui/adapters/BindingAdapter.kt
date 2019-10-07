package kz.dragau.larek.ui.adapters

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tiper.MaterialSpinner
import de.hdodenhof.circleimageview.CircleImageView
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@BindingAdapter("setImage")
fun CircleImageView.setImage(url: String?){
    if(url != null) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("money")
fun TextView.setMoney(money: Int){
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(Locale("kk", "KZ"))
    this.text = format.format(money)
}

@BindingAdapter("fillColor")
fun CircleImageView.fillColor(color: String){
    setColorFilter(Color.parseColor(color))
}

@BindingAdapter("setImageUri")
fun ImageView.setImageUri(uri: String?){ //, newImageAttrChanged: InverseBindingListener){
    if(uri == null)
        return
    Glide.with(context).load(uri).into(this)

}

@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun MaterialSpinner.bindSpinnerData(newSelectedValue: String?, newTextAttrChanged: InverseBindingListener){
    this.onItemSelectedListener = object:
        MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
            if(newSelectedValue != null && newSelectedValue.equals(parent.selectedItem))
                return
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: MaterialSpinner) {
        }
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun MaterialSpinner.captureSelectedValue(): String{
    return selectedItem.toString()
}