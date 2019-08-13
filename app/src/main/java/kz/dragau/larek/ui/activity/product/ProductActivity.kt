package kz.dragau.larek.ui.activity.product

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.product.ProductView
import kz.dragau.larek.presentation.presenter.product.ProductPresenter

import                  kz.dragau.larek.ui.activity.BaseActivity;
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import kz.dragau.larek.ui.fragment.product.ProductRegisterFragment
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import timber.log.Timber


class ProductActivity : BaseActivity(), ProductView {
    companion object {
        const val TAG = "ProductActivity"
        fun getIntent(context: Context): Intent = Intent(context, ProductActivity::class.java)
        val PRODUCT_TRANSITION = "product_transition"
    }

    @InjectPresenter
    lateinit var mProductPresenter: ProductPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.ProductRegisterScreen())))
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

    var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_product_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is ProductRegisterFragment
                && nextFragment == null
            ) {
                setupSharedElement(
                    (currentFragment as ProductRegisterFragment?)!!,
                    nextFragment,
                    fragmentTransaction!!
                )
            }
        }
    }

//    Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        Log.d("onActivityResult", "onActivityResult: .");
//        if (resultCode == Activity.RESULT_OK) {
//            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//            String re = scanResult.getContents();
//            String message = re;
//            Log.d("onActivityResult", "onActivityResult: ." + re);
//            Result handlerResult = new Result(Result.STATUS_SUCCESS, "qrcode", message);
//            resultHandler.onHandleResult(handlerResult);
//        }
//        // else continue with any other code you need in the method
//        this.finish();
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(resultCode == Activity.RESULT_OK){
//            var scanResult: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//            var re = scanResult.contents
//            Timber.i("Message from result " + re)
//
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun setupSharedElement(
        productRegisterFragment: ProductRegisterFragment,
        nextFragment: Nothing? = null,
        fragmentTransaction: FragmentTransaction
    ) {
        val changeBounds = ChangeBounds()//.apply { duration = 10000 }
        productRegisterFragment.sharedElementEnterTransition = changeBounds
        productRegisterFragment.sharedElementReturnTransition = changeBounds
//
        val view = productRegisterFragment.binding.makePhotoBtn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.transitionName = LoginInActivity.LOGIN_TRANSITION
        }
        fragmentTransaction.addSharedElement(view , ProductActivity.PRODUCT_TRANSITION)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}