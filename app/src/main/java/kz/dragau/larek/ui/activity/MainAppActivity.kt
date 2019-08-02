package kz.dragau.larek.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.R
import kz.dragau.larek.extensions.showErrorAlertDialog
import kz.dragau.larek.presentation.presenter.MainAppPresenter
import kz.dragau.larek.presentation.view.MainAppView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainAppActivity : BaseActivity(), MainAppView {
    private val SPLASH_DELAY: Long = 1000

    private val mHandler = Handler()
    private val mLauncher = Launcher()
    private var disposable: Disposable? = null

    companion object {
        const val TAG = "MainAppActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainAppActivity::class.java)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var mMainAppPresenter: MainAppPresenter


    @ProvidePresenter
    fun providePresenter(): MainAppPresenter
    {
        return MainAppPresenter(router)
    }

    private val navigator = SupportAppNavigator(this, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        isDynamicThemingOn = false
        isFullScreen = true

        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /*override fun showLogin() {
        if (!isFinishing) {
            val intent = Intent(this, LoginInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.startActivity(intent)
            finish()
        }
    }*/


    override fun showError(exception: Throwable) {
        if (errorDialog == null || (errorDialog != null && !errorDialog!!.isShowing))
        {
            val responseBody = if (exception is HttpException) {
                exception.response().errorBody()?.string()
            } else {
                null
            }
            errorDialog = showErrorAlertDialog({
                cancelable = false
                closeIconClickListener {
                    mMainAppPresenter.auth()
                }
            }, getNetworkErrorTitle(exception, responseBody), null)
            errorDialog?.show()
        }
    }

    override fun onStart() {
        super.onStart()

        mHandler.postDelayed(mLauncher, SPLASH_DELAY)
    }

    override fun onStop() {
        mHandler.removeCallbacks(mLauncher)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun launch()
    {
        if (!isFinishing)
        {
            mMainAppPresenter.showLogin()
        }
    }

    override fun goToHome() {
        /*if (!isFinishing) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.startActivity(intent)
            finish()
        }*/
    }

    override fun showTutorial() {
        /*val intent = TutorialActivity.getIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)*/
    }


    private inner class Launcher : Runnable {
        override fun run() {
            launch()
        }
    }

    private fun unbindDrawables(view: View) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null)
        }
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount) {
                unbindDrawables((view as ViewGroup).getChildAt(i))
            }
            (view as ViewGroup).removeAllViews()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindDrawables(findViewById(R.id.root))
        System.gc()
    }
}
