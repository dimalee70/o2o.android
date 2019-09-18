package kz.dragau.larek.ui.fragment.store

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.ImageViewPagerView
import kz.dragau.larek.presentation.presenter.store.ImageViewPagerPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_show_image.*
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.databinding.FragmentImageViewPagerBinding
import kz.dragau.larek.databinding.ImageViewpagerItemBinding
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.ui.adapters.images.ImagePagerAdapter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ImageViewPagerFragment : BaseMvpFragment(), ImageViewPagerView {
    companion object {
        const val TAG = "ImageViewPagerFragment"

        fun newInstance(position: Int): ImageViewPagerFragment {
            val fragment: ImageViewPagerFragment = ImageViewPagerFragment()
            val args: Bundle = Bundle()
            args.putInt(Constants.PHOTO_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mImageViewPagerPresenter: ImageViewPagerPresenter

    @ProvidePresenter
    fun providePressenter(): ImageViewPagerPresenter{
        return ImageViewPagerPresenter(router)
    }

    @Inject
    lateinit var imageList: Images

    lateinit var binding: FragmentImageViewPagerBinding

    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        position = arguments!!.getInt(Constants.PHOTO_POSITION, 0)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_view_pager,
            container, false)
        binding.imageVp.adapter = ImagePagerAdapter(context!!, imageList.images!!)
        binding.imageVp!!.addOnPageChangeListener(ImageChangeListener(imageList, activity!!.pageTv!!))
        activity!!.pageTv!!.text = (binding.imageVp.currentItem + 1).toString() + " из " + imageList.images!!.size
        setHasOptionsMenu(true)
        binding.imageVp.currentItem = position!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        activity!!.pageTv!!.text = (binding.imageVp.currentItem + 1).toString() + " из " + imageList.images!!.size
    }

    override fun onStop() {
        super.onStop()
        activity!!.pageTv!!.text = ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_photo, menu)
    }

    class ImageChangeListener(private var imageList: Images, private var textView: TextView): ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            return
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            return
        }

        override fun onPageSelected(position: Int) {
            textView.text = (position + 1).toString() + " из " + imageList.images!!.size
        }
    }
}
