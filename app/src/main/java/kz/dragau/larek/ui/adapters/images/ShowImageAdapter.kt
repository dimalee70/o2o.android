package kz.dragau.larek.ui.adapters.images

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.databinding.ImageItemBinding
import kz.dragau.larek.databinding.ImageShowItemBinding
import kz.dragau.larek.presentation.presenter.store.ShowImageFragmentPressenter
import kz.dragau.larek.ui.adapters.images.BaseImageAdapter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ShowImageAdapter(private val context: Context, private var images: Array<String>, private var router: Router): BaseImageAdapter<String>(context, images) {


    lateinit var showImageItemBinding: ImageShowItemBinding

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val image = images[position]
        showImageItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(viewGroup!!.context),
                R.layout.image_show_item, viewGroup, false)
        showImageItemBinding.avaIv.setOnClickListener{
            openViewPager(position)
        }
        Glide.with(showImageItemBinding.root).load(image).into(showImageItemBinding.avaIv)
        return showImageItemBinding.root
    }

    private fun openViewPager(position: Int) {
        router.navigateTo(Screens.ImageViewPagerScreen(position))
    }

//    lateinit var imageItemBinding: ImageItemBinding
//
//    @SuppressLint("ViewHolder", "SetTextI18n")
//    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
//        val image = images[position]
//        imageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup!!.context),
//            R.layout.image_item, viewGroup, false)
//
////        if (view == null){
////            val viewHolder = ViewHolder(imageItemBinding.avaIv)
////            imageItemBinding.root.tag = viewHolder
////        }
//
////        val viewHolder = imageItemBinding.root.tag as ViewHolder
////        val viewHolder = ViewHolder(imageItemBinding.avaIv)
////        imageItemBinding.root.tag = viewHolder
//
////        imageItemBinding.avaIv.setImageResource(R.drawable.splash_screen)
//        if(position == 2 && images.size > 3){
//            imageItemBinding.transparentV.visibility = View.VISIBLE
//            imageItemBinding.countPhotoTv.visibility  = View.VISIBLE
//            imageItemBinding.countPhotoTv.text = images.size.toString() + "\nфото"
//            imageItemBinding.transparentV.setOnClickListener{
//                showImagesActivity()
//            }
//        }
//        Glide.with(imageItemBinding.root).load(image).into(imageItemBinding.avaIv)
//        return imageItemBinding.root
//
//    }
//
//    private fun showImagesActivity() {
//
//        router.navigateTo(Screens.ImagesScreen())
//    }
}