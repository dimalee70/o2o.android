package kz.dragau.larek.ui.fragment.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import kz.dragau.larek.presentation.presenter.store.RegisterStorePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment

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

    @InjectPresenter
    lateinit var mRegisterStorePresenter: RegisterStorePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
