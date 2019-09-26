package kz.dragau.larek.ui.activity.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_store.*
import kz.dragau.larek.App
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.home.HomeView
import kz.dragau.larek.presentation.presenter.home.HomePresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.activity.LoginInActivity
import kz.dragau.larek.ui.activity.product.ProductActivity
import kz.dragau.larek.ui.fragment.home.HomeMainFragment
import kz.dragau.larek.ui.fragment.product.ProductRegisterFragment
import kz.dragau.larek.ui.fragment.store.ShowImageFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class HomeActivity : BaseActivity(), HomeView {
    companion object {
        const val TAG = "HomeActivity"
        fun getIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mHomePresenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter{
        return HomePresenter(router)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.HomeMainScreen())))
        }
        setContentView(R.layout.activity_home)
        setupToolbar()
        setupNavigationDrawer(homeToolbar)

    }

    private fun setupToolbar() {
        homeToolbar.title = ""
        setSupportActionBar(homeToolbar)
    }

    private fun setupNavigationDrawer(toolbar: Toolbar?) {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            homeToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ){
        }
//        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_navigation)
//        homeToolbar.setNavigationIcon(R.drawable.ic_navigation)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = false
        actionBarDrawerToggle.setToolbarNavigationClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_navigation)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeMainFragment -> {
                    drawerLayout.closeDrawers()
                    router.navigateTo(Screens.HomeMainScreen())
                    return@setNavigationItemSelectedListener true
                }
                R.id.springAnimationFragment -> {
                    drawerLayout.closeDrawers()
                    router.navigateTo(Screens.ScanScreen())
                    return@setNavigationItemSelectedListener true
                }
                else ->{
                    return@setNavigationItemSelectedListener false
                }
            }
        }
//        val navController = Navigation.findNavController(this, R.id.physics_animation_nav_fragment)
//        navigationView.setupWithNavController(navController)
    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        navigationView.menu.getItem(0).isChecked = true

    }



    var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_home_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is HomeMainFragment
                && nextFragment == null
            ) {
                setupSharedElement(
                    (currentFragment as HomeMainFragment?)!!,
                    nextFragment,
                    fragmentTransaction!!
                )
            }
        }
    }

    private fun setupSharedElement(
        showImageFragment: HomeMainFragment,
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



    override fun onBackPressed() {
        super.onBackPressed()
    }
}
