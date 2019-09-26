package kz.dragau.larek

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.activity.MainAppActivity
import kz.dragau.larek.ui.activity.crop.CropActivity
import kz.dragau.larek.ui.activity.home.HomeActivity
import kz.dragau.larek.ui.activity.product.ProductActivity
import kz.dragau.larek.ui.activity.product.ScanActivity
import kz.dragau.larek.ui.activity.product.AddProductActivity
import kz.dragau.larek.ui.activity.store.ShowImageActivity
import kz.dragau.larek.ui.activity.store.StoreActivity
import kz.dragau.larek.ui.fragment.confirm.ConfirmCodeFragment
import kz.dragau.larek.ui.fragment.home.HomeMainFragment
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import kz.dragau.larek.ui.fragment.map.LocationMapFragment
import kz.dragau.larek.ui.fragment.product.ProductRegisterFragment
import kz.dragau.larek.ui.fragment.registration.RegistrationFragment
import kz.dragau.larek.ui.fragment.store.ImageViewPagerFragment
import kz.dragau.larek.ui.fragment.store.ShowImageFragment
import kz.dragau.larek.ui.fragment.store.StoreRegisterFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainAppActivity::class.java)
        }
    }

    class ImagesScreen(private var position: Int? = null) : SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            position?.let {
                val intent = Intent(context, ShowImageActivity::class.java)
                intent.putExtra(Constants.PHOTO_POSITION, position!!)
                return intent
            }
            return Intent(context, ShowImageActivity::class.java)
        }
    }

//    class CropScreen(): SupportAppScreen(){
//        override fun getActivityIntent(context: Context?): Intent {
//            return Intent(context, CropActivity::class.java)
//        }
//    }
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

//    class GalleryScreen: SupportAppScreen(){
//        override fun getActivityIntent(context: Context?): Intent {
//            return Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        }
//    }
//
//    class CameraScreen: SupportAppScreen(){
//        override fun getActivityIntent(context: Context?): Intent {
//            return Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
//        }
//    }

    class StoreScreen: SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, StoreActivity::class.java)
        }
    }

    class HomeScreen: SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, HomeActivity::class.java)
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

    class AddProductScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, AddProductActivity::class.java)
        }
    }

    class SmsCodeScreen(userRequstModel: LoginRequestModel?): SupportAppScreen() {
        var userRequstModel : LoginRequestModel? = null
        init {
            this.screenKey = javaClass.simpleName
            this.userRequstModel = userRequstModel
        }

        override fun getFragment(): Fragment {
            return SmsCodeFragment.newInstance(userRequstModel)
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

    class StoreRegisterScreen: SupportAppScreen(){
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return StoreRegisterFragment.newInstance()
        }
    }

    class ShowImagesScreen : SupportAppScreen(){
        init {
            this.screenKey =  javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return ShowImageFragment.newInstance()
        }
    }

    class ImageViewPagerScreen(private var position: Int): SupportAppScreen(){
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return ImageViewPagerFragment.newInstance(position)
        }
    }

    class HomeMainScreen: SupportAppScreen(){
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return HomeMainFragment.newInstance()
        }
    }

}