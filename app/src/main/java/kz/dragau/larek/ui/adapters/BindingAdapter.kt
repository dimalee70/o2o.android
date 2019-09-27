package kz.dragau.larek.ui.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat
import java.util.*

@BindingAdapter("setImage")
fun CircleImageView.setImage(url: String){
    Glide.with(context)
        .load(url)
//        .apply(RequestOptions()
//            .circleCrop())
        .into(this)
}

@BindingAdapter("money")
fun TextView.setMoney(money: Int){
    var format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(Locale("kk", "KZ"))
    this.text = format.format(money)
}