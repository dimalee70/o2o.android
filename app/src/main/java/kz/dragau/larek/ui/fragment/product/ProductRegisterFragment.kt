package kz.dragau.larek.ui.fragment.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.product.ProductRegisterView
import kz.dragau.larek.presentation.presenter.product.ProductRegisterPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.fragment_product_register.*
import kz.dragau.larek.App
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import kz.dragau.larek.api.response.ProductCategoriesResponce
import kz.dragau.larek.databinding.FragmentProductRegisterBinding
import kz.dragau.larek.models.objects.ProductCategories
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class ProductRegisterFragment : BaseMvpFragment(), ProductRegisterView {

    companion object {
        const val TAG = "ProductRegisterFragment"

        fun newInstance(): ProductRegisterFragment {
            val fragment: ProductRegisterFragment = ProductRegisterFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mProductRegisterPresenter: ProductRegisterPresenter

    @Inject
    lateinit var productRegisterViewModel: ProductRegisterViewModel

    lateinit var binding: FragmentProductRegisterBinding

    private val lifecycleRegistry = LifecycleRegistry(this)

    @ProvidePresenter
    fun providePresenter(): ProductRegisterPresenter{
        return ProductRegisterPresenter(router, productRegisterViewModel)
    }

    private val listener by lazy {
        object : MaterialSpinner.OnItemSelectedListener {
            @SuppressLint("TimberArgCount")
            override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                Timber.d("MaterialSpinner", "onItemSelected parent=${parent.id}, position=$position")
                parent.focusSearch(View.FOCUS_UP)?.requestFocus()
            }

            @SuppressLint("TimberArgCount")
            override fun onNothingSelected(parent: MaterialSpinner) {
                Timber.d("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        mProductRegisterPresenter.attachLifecycle(lifecycleRegistry)
        mProductRegisterPresenter.observeForProductCategoriesResponseBoundary()
            .observe(this, Observer {
                response -> response.let {
                    setProductCategories(response)
            }
            })
    }

    private fun setProductCategories(response: ProductCategoriesResponce){

        ArrayAdapter<ProductCategories>(context!!, android.R.layout.simple_list_item_1, response.resultObject!!)
            .let {
                it.setDropDownViewResource(R.layout.item_spinner_simple)
                binding.productCategoryMs.apply {
                    adapter = it
                    onItemSelectedListener = listener
                }
            }
//        ArrayAdapter.createFromResource(context!!, response.resultObject!!, android.R.layout.simple_list_item_1

//        ).let {
//            it.setDropDownViewResource(R.layout.item_spinner_simple)
//            binding.productCategoryMs.apply {
//                adapter = it
//                onItemSelectedListener = listener
//            }
////            spinner.adapter = it
////            appCompatSpinner.adapter = it
//        }
    }

    private fun MaterialSpinner.onClick() {
        error = if (error.isNullOrEmpty()) resources.getText(R.string.error) else null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        pageTv.let { it.setText(R.string.titleRegisterProduct) }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_register, container, false)
//        val frVew = binding.flMain
        binding.productRegisterViewModel = productRegisterViewModel
        binding.presenter = mProductRegisterPresenter
        mProductRegisterPresenter.getProductCategoris()
//        ArrayAdapter.createFromResource(context!!, R.array.categories_array, android.R.layout.simple_list_item_1
//
//        ).let {
//            it.setDropDownViewResource(R.layout.item_spinner_simple)
//            binding.productCategoryMs.apply {
//            adapter = it
//            onItemSelectedListener = listener
//            }
////            spinner.adapter = it
////            appCompatSpinner.adapter = it
//        }
//        material_spinner_1.let {
//
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        pageTv.let { it.setText(R.string.titleRegisterProduct) }
        activity!!.pageTv.setText(R.string.titleRegisterProduct)


    }

    override fun onResume() {
        super.onResume()
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
        }
    }
}
