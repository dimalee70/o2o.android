package kz.dragau.larek

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.dragau.larek.di.components.AppComponent
import kz.dragau.larek.di.components.DaggerAppComponent
import org.greenrobot.eventbus.EventBus
import com.crashlytics.android.core.CrashlyticsCore
import kz.dragau.larek.di.modules.ApplicationModule
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber.DebugTree
import timber.log.Timber



class App : MultiDexApplication() {

    private var cicerone: Cicerone<Router>? = null

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()
        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        //TypefaceUtil.overrideFont(this, "SERIF", "fonts/trebuchet.ttf")
        //EventBus.getDefault().register(SoundPlayer)
    }

    companion object
    {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }
}