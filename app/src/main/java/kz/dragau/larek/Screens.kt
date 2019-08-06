package kz.dragau.larek

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.activity.MainAppActivity
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainAppActivity::class.java)
        }
    }

    class LoginScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, LoginInActivity::class.java)
        }
    }

    class PhoneNumberScreen : SupportAppScreen() {

        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return PhoneNumberFragment.newInstance()
        }
    }

    class SmsCodeScreen : SupportAppScreen() {
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return SmsCodeFragment.newInstance()
        }
    }
}