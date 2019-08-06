package kz.dragau.larek.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.Screens
import kz.dragau.larek.presentation.view.LoginInView
import kz.dragau.larek.presentation.presenter.LoginInPresenter
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class LoginInActivity : BaseActivity(), LoginInView {
    companion object {
        const val TAG = "LoginInActivity"
        fun getIntent(context: Context): Intent = Intent(context, LoginInActivity::class.java)
    }

    @InjectPresenter
    lateinit var mLoginInPresenter: LoginInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_in)


        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.PhoneNumberScreen())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    var navigator:SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_login_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            /*if (command is Forward
                && currentFragment is ProfileFragment
                && nextFragment is SelectPhotoFragment
            ) {
                setupSharedElementForProfileToSelectPhoto(
                    (currentFragment as ProfileFragment?)!!,
                    (nextFragment as SelectPhotoFragment?)!!,
                    fragmentTransaction!!
                )
            }*/
        }
    }
}
