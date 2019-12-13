package kz.dragau.larek.ui.fragment.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.registration.RegistrationView
import kz.dragau.larek.presentation.presenter.registration.RegistrationPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.databinding.FragmentRegistrationBinding
import kz.dragau.larek.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RegistrationFragment : BaseMvpFragment(), RegistrationView {

    var userRequestModel: LoginRequestModel? = null
    companion object {
        const val EXTRA_CODE_PHONE = "EXTRA_CODE_PHONE"
        const val TAG = "RegistrationFragment"

        fun newInstance(userRequestModel: LoginRequestModel?): RegistrationFragment {
            val fragment: RegistrationFragment = RegistrationFragment()
            val args: Bundle = Bundle()
            args.putSerializable(EXTRA_CODE_PHONE, userRequestModel)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mRegistrationPresenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter
    {
        return RegistrationPresenter(router)
    }

    lateinit var binding: FragmentRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        this.userRequestModel = arguments!!.getSerializable(EXTRA_CODE_PHONE) as LoginRequestModel?
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        val frView: View = binding.flMain
        binding.registrationViewModel = mRegistrationPresenter.registrationRequestModel
        binding.presenter = mRegistrationPresenter
        return frView
    }
}
