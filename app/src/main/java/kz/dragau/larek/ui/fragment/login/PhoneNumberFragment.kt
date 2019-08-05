package kz.dragau.larek.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.login.PhoneNumberView
import kz.dragau.larek.presentation.presenter.login.PhoneNumberPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentPhoneNumberBinding
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PhoneNumberFragment : BaseMvpFragment(), PhoneNumberView {
    companion object {
        const val TAG = "PhoneNumberFragment"

        fun newInstance(): PhoneNumberFragment {
            val fragment: PhoneNumberFragment = PhoneNumberFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mPhoneNumberPresenter: PhoneNumberPresenter

    @ProvidePresenter
    fun providePresenter(): PhoneNumberPresenter
    {
        return PhoneNumberPresenter(router)
    }

    lateinit var binding: FragmentPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_phone_number, container , false)
        var frView : View  = binding.flMain

        binding.loginViewModel = mPhoneNumberPresenter.userRequstModel
        binding.presenter = mPhoneNumberPresenter
        return frView
    }
}
