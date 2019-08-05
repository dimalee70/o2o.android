package kz.dragau.larek.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.login.PhoneNumberView
import kz.dragau.larek.presentation.presenter.login.PhoneNumberPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment

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

    @InjectPresenter
    lateinit var mPhoneNumberPresenter: PhoneNumberPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
