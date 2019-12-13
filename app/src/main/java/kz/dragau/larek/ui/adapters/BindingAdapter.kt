package kz.dragau.larek.ui.adapters

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.*
import androidx.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
import android.graphics.PorterDuff
import android.R.color
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.graphics.drawable.DrawableCompat
import kz.dragau.larek.R


@BindingAdapter("entries", "layout")
fun <T> setEntries(viewGroup: ViewGroup,
                   entries: List<T>?, layoutId: Int){
    viewGroup.removeAllViews()
    if(entries != null){
        val inflater = viewGroup.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (i in entries.indices){
            val entry = entries[i]
            val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, viewGroup, true)
            binding.setVariable(BR.data, entry)
        }
    }
}

@BindingAdapter("entry", "layoutId")
fun <T> setEntry(
    viewGroup: ViewGroup,
    entry: T?, layoutId: Int
) {
    viewGroup.removeAllViews()
    if (entry != null) {
        val inflater = viewGroup.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, viewGroup, true)
        binding.setVariable(BR.data, entry)
    }
}

@BindingAdapter("setImage")
fun ImageView.setImage(url: String){
    if(!url.isNullOrEmpty())
    {
        Glide.with(context)
            .load(url)
            .apply(createOptionForGlide())
            .into(this)
    }
}

@BindingAdapter("setImage")
fun CircleImageView.setImage(url: String?){
    if(url != null) {
        Glide.with(context)
            .load(url)
            .apply(createOptionForGlide())
            .into(this)
    }
}

fun createOptionForGlide(): RequestOptions{
    return RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.progress_animation)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()
}

@BindingAdapter("backgrndColor")
fun LinearLayout.setBackgrndColor(сlr: String){
    setBackgroundColor(Color.parseColor(сlr))
    }

@BindingAdapter("backgrndColor")
fun TextView.setBackgrndColor(clr:String){
    val drawable = ContextCompat.getDrawable(context, R.drawable.test_bg_rounded)
    if(drawable != null ) {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(clr))
        background = wrappedDrawable

    }
//    drawable?.setColorFilter(BlendModeColorFilter(Color.parseColor(clr), BlendMode.SRC_ATOP))
//    background = drawable

//    val drawable = resources.getDrawable(R.drawable.bg_rounded_solid)
//    drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
//    yourTextView.setBackground(drawable)
//    setBackgroundColor(Color.parseColor(clr))
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