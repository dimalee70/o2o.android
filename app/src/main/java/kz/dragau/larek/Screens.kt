package kz.dragau.larek

import android.content.Context
import android.content.Intent
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.activity.MainAppActivity
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
}