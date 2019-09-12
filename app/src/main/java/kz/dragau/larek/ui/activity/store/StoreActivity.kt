package kz.dragau.larek.ui.activity.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ferfalk.simplesearchview.utils.DimensUtils
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_register_store.*
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.presentation.presenter.store.RegisterStorePresenter
import kz.dragau.larek.presentation.view.store.StoreView
import kz.dragau.larek.presentation.presenter.store.StorePresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.fragment.store.StoreRegisterFragment
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class StoreActivity : BaseActivity(), StoreView {

    companion object {
        const val TAG = "StoreActivity"
        fun getIntent(context: Context): Intent = Intent(context, StoreActivity::class.java)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var saleSelector: SaleSelector

    @InjectPresenter
    lateinit var mStorePresenter: StorePresenter

    @ProvidePresenter
    fun providePresenter(): StorePresenter
    {
        return StorePresenter(router, saleSelector)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        toolbar.title = ""
        setSupportActionBar(toolbar)
//        setSearchTollbar()

        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.StoreRegisterScreen())))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupSearchView(menu: Menu?){
        var item: MenuItem = menu!!.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        val revealCenter = searchView.revealAnimationCenter
        revealCenter.x -= DimensUtils.convertDpToPx(Constants.extraRevealCenterPadding, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
//                avatarIv.setImageURI(result.uri)
                mStorePresenter.changeImage(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Croppinf failed: " + result.error, Toast.LENGTH_LONG).show()
            }
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
