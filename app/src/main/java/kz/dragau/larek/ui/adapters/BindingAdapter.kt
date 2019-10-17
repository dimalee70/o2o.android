package kz.dragau.larek.ui.adapters

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tiper.MaterialSpinner
import de.hdodenhof.circleimageview.CircleImageView
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.models.objects.ProductCategories
import org.apache.commons.codec.binary.Base64
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern
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

//    if (){
        val imageAsBytes = android.util.Base64.decode(uri.toByteArray(), android.util.Base64.DEFAULT)
        Glide.with(context).load(imageAsBytes).into(this)
//        return
//    }

//    if(uri.endsWith("=")){

//    }
//    println(Base64.isBase64(uri))
//    if(Base64.isBase64(uri)){
//        return
//    }
//    Glide.with(context).load(uri).into(this)

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
    return (selectedItem as ProductCategories).productCategoryId.toString()
}



//@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
//fun MaterialSpinner.bindSpinnerData(newSelectedValue: String?, newTextAttrChanged: InverseBindingListener){
//    this.onItemSelectedListener = object:
//        MaterialSpinner.OnItemSelectedListener {
//        override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
//            if(newSelectedValue != null && newSelectedValue.equals(parent.selectedItem))
//                return
//                newTextAttrChanged.onChange()
//        }
//
//        override fun onNothingSelected(parent: MaterialSpinner) {
////            newTextAttrChanged.onChange()
//        }
//    }
//}
//
//@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
//fun MaterialSpinner.captureSelectedValue(): String{
////    return (selectedItem as ProductCategories).productCategoryId.toString()
//    return selectedItem.toString()
//}