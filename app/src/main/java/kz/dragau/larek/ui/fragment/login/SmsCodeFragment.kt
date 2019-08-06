package kz.dragau.larek.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.login.SmsCodeView
import kz.dragau.larek.presentation.presenter.login.SmsCodePresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentSmsCodeBinding
import kz.dragau.larek.presentation.presenter.login.PhoneNumberPresenter
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.common.BackButtonListener
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SmsCodeFragment : BaseMvpFragment(), SmsCodeView, BackButtonListener {
    companion object {
        const val TAG = "SmsCodeFragment"

        fun newInstance(): SmsCodeFragment {
            val fragment: SmsCodeFragment = SmsCodeFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mSmsCodePresenter: SmsCodePresenter

    @ProvidePresenter
    fun providePresenter(): SmsCodePresenter
    {
        return SmsCodePresenter(router)
    }

    @Inject
    lateinit var router: Router

    lateinit var binding: FragmentSmsCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_code, container , false)
        val frView : View  = binding.flMain

        binding.presenter = mSmsCodePresenter
        return frView
    }

    override fun onBackPressed(): Boolean {
        mSmsCodePresenter.onBackPressed()
        return true
    }


    lateinit var sms: ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sms = view.findViewById<View>(R.id.avatar_imageView) as ImageView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sms.transitionName = LoginInActivity.LOGIN_TRANSITION
    }
}
