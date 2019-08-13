package kz.dragau.larek.ui.fragment.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.product.ProductRegisterView
import kz.dragau.larek.presentation.presenter.product.ProductRegisterPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentProductRegisterBinding
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

    lateinit var binding: FragmentProductRegisterBinding


    @ProvidePresenter
    fun providePresenter(): ProductRegisterPresenter{
        return ProductRegisterPresenter(router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_register, container, false)
        val frVew = binding.flMain
        binding.presenter = mProductRegisterPresenter
        return frVew
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }
}
