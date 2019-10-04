package kz.dragau.larek.ui.adapters.images

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.databinding.ItemImageBinding
import ru.terrakok.cicerone.Router

class ImageAdapter(private val context: Context, private var images: ArrayList<Uri>, private var router: Router): BaseImageAdapter<Uri>(context, images) {

    lateinit var imageItemBinding: ItemImageBinding

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val image = images[position]
        imageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup!!.context),
            R.layout.item_image, viewGroup, false)

//        if (view == null){
//            val viewHolder = ViewHolder(imageItemBinding.avaIv)
//            imageItemBinding.root.tag = viewHolder
//        }

//        val viewHolder = imageItemBinding.root.tag as ViewHolder
//        val viewHolder = ViewHolder(imageItemBinding.avaIv)
//        imageItemBinding.root.tag = viewHolder

//        imageItemBinding.avaIv.setImageResource(R.drawable.splash_screen)
        if(position == 2 && images.size > 3){
            imageItemBinding.transparentV.visibility = View.VISIBLE
            imageItemBinding.countPhotoTv.visibility  = View.VISIBLE
            imageItemBinding.countPhotoTv.text = images.size.toString() + "\nфото"
            imageItemBinding.transparentV.setOnClickListener{
                showImagesActivity()
            }
        }
        imageItemBinding.avaIv.setOnClickListener {
            showImagePager(position)
        }
//        else if(position != 2){
//            showImagePager(position)
//        }
//        imageItemBinding.avaIv.setImageURI(image)
//        Glide.with(imageItemBinding.root).load(image).into(imageItemBinding.avaIv)
        Glide.with(imageItemBinding.root).load(image.path).into(imageItemBinding.avaIv)
        return imageItemBinding.root

    }

    private fun showImagePager(position: Int) {
//        Toast.makeText(context, "Click Image", Toast.LENGTH_SHORT).show()
//        router.navigateTo(Screens.ImageViewPagerScreen(position))
//        navigator.applyCommands(arrayOf<Command>(Replace(Screens.ImageViewPagerScreen(0))))
        router.navigateTo(Screens.ImagesScreen(position))
    }

    private fun showImagesActivity() {

        router.navigateTo(Screens.ImagesScreen())
    }

}