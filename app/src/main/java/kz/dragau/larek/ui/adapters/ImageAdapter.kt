package kz.dragau.larek.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kz.dragau.larek.R
import kz.dragau.larek.databinding.ImageItemBinding

class ImageAdapter(private val context: Context, private var images: Array<String>): BaseAdapter() {

    lateinit var imageItemBinding: ImageItemBinding

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val image = images[position]
        imageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup!!.context),
            R.layout.image_item, viewGroup, false)

//        if (view == null){
//            val viewHolder = ViewHolder(imageItemBinding.avaIv)
//            imageItemBinding.root.tag = viewHolder
//        }

//        val viewHolder = imageItemBinding.root.tag as ViewHolder
//        val viewHolder = ViewHolder(imageItemBinding.avaIv)
//        imageItemBinding.root.tag = viewHolder

//        imageItemBinding.avaIv.setImageResource(R.drawable.splash_screen)
        if(position == 2){
            imageItemBinding.transparentV.visibility = View.VISIBLE
            imageItemBinding.countPhotoTv.visibility  = View.VISIBLE
            imageItemBinding.countPhotoTv.text = images.size.toString() + "\nфото"
            imageItemBinding.transparentV.setOnClickListener{
                showImagesActivity()
            }
        }
        Glide.with(imageItemBinding.root).load(image).into(imageItemBinding.avaIv)
        return imageItemBinding.root

    }

    private fun showImagesActivity() {
        Toast.makeText(context, "Show ImageActivity", Toast.LENGTH_SHORT).show()
    }

    override fun getItem(position: Int): String {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return images.size
    }

//    class ViewHolder(val imageView: ImageView){
//
//    }

}