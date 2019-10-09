package kz.dragau.larek.ui.activity.customs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.customs.CustomsView
import kz.dragau.larek.presentation.presenter.customs.CustomsPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_customs.*
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.fragment.customs.OnlineCustomsFragment
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class CustomsActivity : BaseActivity(), CustomsView {
    companion object {
        const val TAG = "CustomsActivity"
        fun getIntent(context: Context): Intent = Intent(context, CustomsActivity::class.java)
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mCustomsPresenter: CustomsPresenter

    @ProvidePresenter
    fun providePressenter(): CustomsPresenter{
        return CustomsPresenter(router)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customs)

        custom_back_iv.setOnClickListener{
            onBackPressed()
        }

        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.OnlineCustomsScreen())))
        }
    }

//    private fun setupToolbar() {
////        customsToobar.title = ""
////        setSupportActionBar(customsToobar)
//    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_customs_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is OnlineCustomsFragment
                && nextFragment == null
            ) {
                setupSharedElement(
                    (currentFragment as OnlineCustomsFragment?)!!,
                    nextFragment,
                    fragmentTransaction!!
                )
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setupSharedElement(
        showImageFragment: OnlineCustomsFragment,
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
//        fragmentTransaction.addSharedElement(view , HomeActivity.PRODUCT_TRANSITION)
    }

}
