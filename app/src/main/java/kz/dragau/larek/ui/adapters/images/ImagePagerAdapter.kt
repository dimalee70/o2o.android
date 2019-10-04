package kz.dragau.larek.ui.adapters.images

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kz.dragau.larek.R
import kz.dragau.larek.databinding.ItemImageViewpagerBinding

class ImagePagerAdapter(private var context: Context,
                        private var images: ArrayList<Uri>): PagerAdapter() {

    lateinit var binding: ItemImageViewpagerBinding

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image = images[position]
        binding = DataBindingUtil.inflate(LayoutInflater.from(container.context),
            R.layout.item_image_viewpager, container, false)
        Glide.with(binding.root).load(image.path).into(binding.imageIv)

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}