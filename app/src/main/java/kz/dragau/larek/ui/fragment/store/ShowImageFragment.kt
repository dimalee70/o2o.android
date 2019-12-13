package kz.dragau.larek.ui.fragment.store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.ShowImageView
import kz.dragau.larek.presentation.presenter.store.ShowImagePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.databinding.FragmentShowImageBinding
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.presentation.presenter.store.ShowImageFragmentPressenter
import kz.dragau.larek.presentation.view.store.ShowImageFragmentView
import kz.dragau.larek.ui.adapters.images.ShowImageAdapter
import kz.dragau.larek.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ShowImageFragment : BaseMvpFragment(), ShowImageFragmentView {

    companion object {
        const val TAG = "ShowImageFragment"

        fun newInstance(): ShowImageFragment {
            val fragment: ShowImageFragment = ShowImageFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var imageList: Images

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mShowImagePresenter: ShowImageFragmentPressenter

    private var showImageAdapter: ShowImageAdapter? = null

    @ProvidePresenter
    fun providePresenter(): ShowImageFragmentPressenter{
        return ShowImageFragmentPressenter(router, imageList.images!!)
    }

    lateinit var binding: FragmentShowImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_image, container, false)
        showImageAdapter = ShowImageAdapter(context!!, imageList.images!!, router )
        binding.imageGv.adapter = showImageAdapter
//        binding.imageGv
        binding.presenter = mShowImagePresenter
        binding.floatingActionButton.setOnClickListener{
//            mShowImagePresenter.addPhoto()
            showPictureDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        showImageAdapter!!.notifyDataSetChanged()
        binding.imageGv.adapter = showImageAdapter
    }

    override fun showPictureDialog() {
        this.activity?.let {
            CropImage.activity(null)
//                .setMaxCropResultSize(1920,1080)
//                .setMinCropResultSize(1920, 100.toPx())
//                .setAspectRatio(3,1)
//                .setRequestedSize(150,50, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(it)
//        this.activity?.let {
//            val intent = CropImage.activity(null)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .getIntent(this.context!!)
//            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        }
    }
}
