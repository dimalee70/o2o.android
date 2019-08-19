package kz.dragau.larek.ui.activity.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.store.StoreView
import kz.dragau.larek.presentation.presenter.store.StorePresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.fragment.store.StoreRegisterFragment
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace


class StoreActivity : BaseActivity(), StoreView {
    companion object {
        const val TAG = "StoreActivity"
        fun getIntent(context: Context): Intent = Intent(context, StoreActivity::class.java)
    }

    @InjectPresenter
    lateinit var mStorePresenter: StorePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.StoreRegisterScreen())))
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

    var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_store_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is StoreRegisterFragment
                && nextFragment == null
            ) {
                setupSharedElement(
                    (currentFragment as StoreRegisterFragment?)!!,
                    nextFragment,
                    fragmentTransaction!!
                )
            }
        }
    }

    private fun setupSharedElement(
        productRegisterFragment: StoreRegisterFragment,
        nextFragment: Nothing? = null,
        fragmentTransaction: FragmentTransaction
    ) {
//        val changeBounds = ChangeBounds()//.apply { duration = 10000 }
//        productRegisterFragment.sharedElementEnterTransition = changeBounds
//        productRegisterFragment.sharedElementReturnTransition = changeBounds
////
//        val view = productRegisterFragment.binding.makePhotoBtn
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            view.transitionName = LoginInActivity.LOGIN_TRANSITION
//        }
//        fragmentTransaction.addSharedElement(view , ProductActivity.PRODUCT_TRANSITION)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
