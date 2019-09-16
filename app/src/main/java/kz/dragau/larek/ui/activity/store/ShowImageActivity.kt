package kz.dragau.larek.ui.activity.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sms_code.*
import kz.dragau.larek.App
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.presentation.view.store.ShowImageView
import kz.dragau.larek.presentation.presenter.store.ShowImagePresenter

import                  kz.dragau.larek.ui.activity.BaseActivity;
import kz.dragau.larek.ui.fragment.store.ShowImageFragment
import kz.dragau.larek.ui.fragment.store.StoreRegisterFragment
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class ShowImageActivity : BaseActivity(), ShowImageView {
    companion object {
        const val TAG = "ShowImageActivity"
        fun getIntent(context: Context): Intent = Intent(context, ShowImageActivity::class.java)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var imageList: Images

    @InjectPresenter
    lateinit var mShowImagePresenter: ShowImagePresenter

    @ProvidePresenter
    fun providePresenter(): ShowImagePresenter {
        return ShowImagePresenter(router, imageList.images!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        if(savedInstanceState == null){
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.ShowImagesScreen())))
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_images_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            if (command is Forward
                && currentFragment is ShowImageFragment
                && nextFragment == null
            ) {
                setupSharedElement(
                    (currentFragment as ShowImageFragment?)!!,
                    nextFragment,
                    fragmentTransaction!!
                )
            }
        }
    }


    private fun setupSharedElement(
        showImageFragment: ShowImageFragment,
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
}
