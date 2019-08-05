package kz.dragau.larek.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.LoginInView
import kz.dragau.larek.presentation.presenter.LoginInPresenter
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment

class LoginInActivity : BaseActivity(), LoginInView {
    companion object {
        const val TAG = "LoginInActivity"
        fun getIntent(context: Context): Intent = Intent(context, LoginInActivity::class.java)
    }

    @InjectPresenter
    lateinit var mLoginInPresenter: LoginInPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_in)

        if (savedInstanceState == null) {
            mLoginInPresenter.showLogin()
        }
    }

    override fun showLogin() {
        var loginFragment = supportFragmentManager.findFragmentById(R.id.activity_login_frame_layout) as PhoneNumberFragment?

        if (loginFragment == null) {
            loginFragment = PhoneNumberFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_login_frame_layout, loginFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
