package kz.dragau.larek

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.api.requests.RegistrationRequestModel
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.activity.MainAppActivity
import kz.dragau.larek.ui.activity.product.ProductActivity
import kz.dragau.larek.ui.activity.product.ScanActivity
import kz.dragau.larek.ui.fragment.confirm.ConfirmCodeFragment
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import kz.dragau.larek.ui.fragment.map.LocationMapFragment
import kz.dragau.larek.ui.fragment.product.ProductRegisterFragment
import kz.dragau.larek.ui.fragment.registration.RegistrationFragment
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

    class ProductScreen: SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, ProductActivity::class.java)
        }
    }

    class ScanScreen: SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, ScanActivity::class.java)
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

    class ConfirmCodeScreen(userRequstModel: LoginRequestModel?): SupportAppScreen()
    {
        var userRequstModel : LoginRequestModel? = null
        init {
            this.screenKey = javaClass.simpleName
            this.userRequstModel = userRequstModel
        }

        override fun getFragment(): Fragment {
            return ConfirmCodeFragment.newInstance(userRequstModel)
        }
    }

    class RegistrationScreen(userRequstModel: LoginRequestModel?): SupportAppScreen() {
        var userRequstModel: LoginRequestModel? = null
        init {
            this.screenKey = javaClass.simpleName
            this.userRequstModel = userRequstModel
        }

        override fun getFragment(): Fragment {
            return RegistrationFragment.newInstance(userRequstModel)
        }
    }

    class LocationMapScreen : SupportAppScreen() {
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return LocationMapFragment.newInstance()
        }
    }

    class ProductRegisterScreen: SupportAppScreen(){
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return ProductRegisterFragment.newInstance()
        }
    }
}