package kz.dragau.larek.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.LoginInView
import kz.dragau.larek.presentation.presenter.LoginInPresenter
import kz.dragau.larek.ui.common.BackButtonListener
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class LoginInActivity : BaseActivity(), LoginInView {

    companion object {
        const val TAG = "LoginInActivity"
        fun getIntent(context: Context): Intent = Intent(context, LoginInActivity::class.java)
        val LOGIN_TRANSITION = "login_transition"
    }

    @InjectPresenter
    lateinit var mLoginInPresenter: LoginInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_in)


        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.LoginScreen())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    var navigator:SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_login_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is PhoneNumberFragment
                && nextFragment is SmsCodeFragment
            ) {
                setupSharedElement(
                    (currentFragment as PhoneNumberFragment?)!!,
                    (nextFragment as SmsCodeFragment?)!!,
                    fragmentTransaction!!
                )
            }
        }
    }

    private fun setupSharedElement(
        phoneFragment: PhoneNumberFragment,
        smsFragment: SmsCodeFragment,
        fragmentTransaction: FragmentTransaction
    ) {
        val changeBounds = ChangeBounds()//.apply { duration = 10000 }
        smsFragment.sharedElementEnterTransition = changeBounds
        smsFragment.sharedElementReturnTransition = changeBounds
        phoneFragment.sharedElementEnterTransition = changeBounds
        phoneFragment.sharedElementReturnTransition = changeBounds

        /*val view = phoneFragment.binding.
        view.transitionName = LOGIN_TRANSITION
        fragmentTransaction.addSharedElement(view , LOGIN_TRANSITION)*/
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.activity_login_frame_layout)
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }
}
