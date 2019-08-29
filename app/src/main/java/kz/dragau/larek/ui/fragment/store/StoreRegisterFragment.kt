package kz.dragau.larek.ui.fragment.store

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import kz.dragau.larek.presentation.presenter.store.RegisterStorePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentRegisterStoreBinding
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.MainAppPresenter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class StoreRegisterFragment : BaseMvpFragment(), RegisterStoreView {

    companion object {
        const val TAG = "StoreRegisterFragment"

        fun newInstance(): StoreRegisterFragment {
            val fragment: StoreRegisterFragment = StoreRegisterFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var saleSelector: SaleSelector

    @InjectPresenter
    lateinit var mRegisterStorePresenter: RegisterStorePresenter

    @ProvidePresenter
    fun providePresenter(): RegisterStorePresenter
    {
        return RegisterStorePresenter(router, saleSelector)
    }

    lateinit var binding: FragmentRegisterStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_store, container, false)
        binding.presenter = mRegisterStorePresenter

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        saleSelector.salesOuter?.let { showSale(it) }
    }
    override fun showSale(salesOuter: SalesOuter) {
        binding.storeTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
        binding.storeLegalTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.legacyName)
        binding.storeAddressEt.text = Editable.Factory.getInstance().newEditable(salesOuter.address)

    }

//    override fun onDestroy() {
//        saleSelector.listener = null
//        super.onDestroy()
//    }

}
