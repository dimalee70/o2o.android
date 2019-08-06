package kz.dragau.larek.ui.fragment.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.confirm.ConfirmCodeView
import kz.dragau.larek.presentation.presenter.confirm.ConfirmCodePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentConfirmCodeBinding
import kz.dragau.larek.presentation.presenter.login.PhoneNumberPresenter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ConfirmCodeFragment : BaseMvpFragment(), ConfirmCodeView {
    var code: String? = null

    companion object {
        const val EXTRA_CODE = "EXTRA_CODE"
        const val TAG = "ConfirmCodeFragment"

        fun newInstance(code: String?): ConfirmCodeFragment {
            val fragment: ConfirmCodeFragment = ConfirmCodeFragment()
            val args: Bundle = Bundle()
            args.putString(EXTRA_CODE, code)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

//    lateinit var binding: Fra
//            FragmentPhoneNumberBinding

    @InjectPresenter
    lateinit var mConfirmCodePresenter: ConfirmCodePresenter

    @ProvidePresenter
    fun providePresenter(): ConfirmCodePresenter
    {
        return ConfirmCodePresenter(router, code)
    }

    lateinit var binding: FragmentConfirmCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        this.code = arguments?.getString(EXTRA_CODE, null)
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        println(code)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_code, container , false)
        val frView : View  = binding.flMain

        binding.confirmViewModel = mConfirmCodePresenter.confirmRequestModel
        binding.presenter = mConfirmCodePresenter
        return frView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
