package kz.dragau.larek.ui.activity

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.bumptech.glide.load.engine.GlideException
import com.google.firebase.analytics.FirebaseAnalytics
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.R
import kz.dragau.larek.extensions.showErrorAlertDialog
import kz.dragau.larek.moxy.MvpActivity
import kz.dragau.larek.presentation.BaseView
import kz.dragau.larek.presentation.presenter.dialogs.DelayedProgressDialog
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

open class BaseActivity : MvpActivity(), BaseView {
    val BASE_TAG: String = "BaseActivity"

    private lateinit var currentTheme: String

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var sharedPref: SharedPreferences

    //internal var navigator: SupportAppNavigator? = null

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    protected var isDynamicThemingOn: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    protected var isFullScreen: Boolean = false

    private var progressDialog: DelayedProgressDialog? = null
    var errorDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        if (!isDynamicThemingOn && isFullScreen)
        {
            return
        }

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = if (isDynamicThemingOn)
        {
            sharedPref.getString(Constants.themePrefsKey, Constants.darkTheme)!!
        } else {
            Constants.darkTheme
        }
        setAppTheme(currentTheme)
    }

    override fun onResume() {
        super.onResume()

        if (!isDynamicThemingOn && isFullScreen)
        {
            return
        }


        if (isDynamicThemingOn)
        {
            val theme = sharedPref.getString(Constants.themePrefsKey, Constants.darkTheme)
            if (currentTheme != theme)
                recreate()
        }
        else
        {
            if (currentTheme != Constants.darkTheme) {
                recreate()
            }
        }
    }

    fun setAppTheme(currentTheme: String) {
        when (currentTheme)
        {
            Constants.lightTheme -> {
                setTheme(R.style.Theme_App_Light)
            }
            else -> {
                setTheme(R.style.Theme_App_Dark)
            }
        }
    }

    override fun showError(exception: Throwable) {
        runOnUiThread {
            if (errorDialog == null || (errorDialog != null && !errorDialog!!.isShowing)) {
                val responseBody = if (exception is HttpException){
                    exception.response().errorBody()?.string()
                } else{
                    null
                }
                errorDialog = showErrorAlertDialog({
                    cancelable = true
                    closeIconClickListener {
                        Log.d(BASE_TAG, "Error dialog close button clicked")
                    }
                }, getNetworkErrorTitle(exception, responseBody), getErrorMessage(exception, responseBody))
                errorDialog?.show()
            }
        }
    }

    override fun showError(message: String, codeError: Int) {
        runOnUiThread {
            if (errorDialog == null) {
                errorDialog = showErrorAlertDialog({
                    cancelable = true
                    closeIconClickListener {
                        Log.d(BASE_TAG, "Error dialog close button clicked")
                    }
                }, "Ошибка", message)
                errorDialog?.show()
            }
        }
    }

    override fun hideProgress() {
        runOnUiThread {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressDialog?.cancel()
            progressDialog = null
        }
    }

    override fun showProgress() {
        runOnUiThread {
            if (progressDialog == null)
                progressDialog = DelayedProgressDialog(this)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            progressDialog?.show()
        }
    }

    override fun showRequestSuccessfully(message: String) {

    }

    fun getNetworkErrorTitle(error: Throwable, responseBody: String?): String
    {
        if (error is HttpException)
        {
            when (error.code())
            {
                404 -> return getString(R.string.bad_server_response)
                //500 -> return getString(R.string.default_unexpected_error_message)
                //502 -> return getString(R.string.default_error_message)
            }
            return getErrorTitle(responseBody)
        } else if (error is SocketTimeoutException)
        {
            return getString(R.string.timed_out)
        } else if (error is IOException)
        {
            return getString(R.string.network_connection_lost)
        }
        else if (error is GlideException)
        {
            return getString(R.string.bad_connection)
        }

        return if (error.localizedMessage != null) getString(R.string.unknown_error) else ""
    }

    private fun getErrorTitle(responseBody: String?): String {
        /*try {
            val jsonObject = JSONObject(responseBody)

            if (jsonObject.has("data")) {
                val messageId = jsonObject.getString("data")
                return getString(
                    try {
                        ErrorResourceTypeDescription.valueOf(messageId).messageId
                    } catch (ex: IllegalArgumentException) {
                        R.string.unknown_error_from_backend
                    }
                )
            }

            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.localizedMessage
        }*/

        return "error"
    }


    private fun getErrorMessage(exception: Throwable, responseBody: String?): String? {
        /*if (exception !is HttpException) {

            return null
        }
        try {
            val jsonObject = JSONObject(responseBody)

            if (jsonObject.has("data")) {
                val messageId = jsonObject.getString("data")
                return getString(ErrorResourceTypeDescription.valueOf(messageId).messageId)
            }

            return null
        } catch (e: Exception) {
            return null
        }*/
        return "error"
    }

    override fun onDestroy() {
        super.onDestroy()

        if (progressDialog != null)
        {
            progressDialog?.dialog?.setOnDismissListener(null)
            progressDialog?.cancel()
        }

        if (errorDialog != null)
        {
            errorDialog?.setOnDismissListener(null)
            errorDialog?.dismiss()
        }
    }
}