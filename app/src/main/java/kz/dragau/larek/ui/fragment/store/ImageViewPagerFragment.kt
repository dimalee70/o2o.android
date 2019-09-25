package kz.dragau.larek.ui.fragment.store

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.ImageViewPagerView
import kz.dragau.larek.presentation.presenter.store.ImageViewPagerPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_show_image.*
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.databinding.FragmentImageViewPagerBinding
import kz.dragau.larek.databinding.ImageViewpagerItemBinding
import kz.dragau.larek.extensions.showConfirmAlertDialog
import kz.dragau.larek.extensions.showErrorAlertDialog
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.ui.adapters.images.ImagePagerAdapter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
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

    var confirmDialog: AlertDialog? = null

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
        binding.imageVp.adapter!!.notifyDataSetChanged()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_crop -> {
                mImageViewPagerPresenter.showPictureDialog()
//                Toast.makeText(context!!, "Crop", Toast.LENGTH_LONG).show()
            }
            R.id.action_delete -> {
//                showConfirm()
                mImageViewPagerPresenter.showConfirmDialog()
            }
        }
        return true
    }

    override fun showPictureDialog() {

//        Toast.makeText(context!!, "showPictureDialog", Toast.LENGTH_SHORT).show()


        this.activity?.let {
            CropImage.activity(null)
//                .setMaxCropResultSize(1920,1080)
//                .setMinCropResultSize(1920, 100.toPx())
//                .setAspectRatio(3,1)
//                .setRequestedSize(150,50, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(it)
        }
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

    override fun showConfirm() {
        if(confirmDialog == null){
            confirmDialog = showConfirmAlertDialog ({
                cancelable = true
                yesBtnClickListener{
                    imageList.images!!.removeAt(binding.imageVp.currentItem)
                    if(imageList.images!!.size == 0){
                        router.exit()
                    }
                    binding.imageVp.adapter!!.notifyDataSetChanged()
                    activity!!.pageTv!!.text = (binding.imageVp.currentItem + 1).toString() + " из " + imageList.images!!.size
                }
                noBtnClickListener(dismissFunc)
            }, R.string.confirm_title,  R.string.confirm_message)
        }
        confirmDialog?.show()
    }

//    errorDialog = showErrorAlertDialog({
//        cancelable = false
//        closeIconClickListener {
//            mMainAppPresenter.auth()
//        }
//    }, getNetworkErrorTitle(exception, responseBody), null)
//    errorDialog?.show()


//    override fun showError(message: String?, codeError: Int) {
//        if (errorDialog == null)
//        {
//            var msg = message
//            if (msg == null)
//            {
//                msg = getString(codeError)
//            }
//
//            errorDialog = showErrorAlertDialog({
//                cancelable = true
//                closeIconClickListener {
//                    Timber.d(BASE_TAG, "Error dialog close button clicked")
//                }
//            }, "Ошибка", msg)
//            errorDialog?.show()
//        }
//    }

}
