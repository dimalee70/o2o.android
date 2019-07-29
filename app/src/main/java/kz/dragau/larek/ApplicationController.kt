package kz.dragau.larek

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.dragau.larek.di.components.AppComponent
import kz.dragau.larek.di.components.DaggerAppComponent
import kz.dragau.larek.di.modules.AppModule
import kz.dragau.larek.di.modules.ContextModule
import org.greenrobot.eventbus.EventBus

class ApplicationController : MultiDexApplication() {
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .contextModule(ContextModule(this))
            //.loginModule(LoginModule(null))
            .build()
        Fabric.with(this, Crashlytics())
        //TypefaceUtil.overrideFont(this, "SERIF", "fonts/trebuchet.ttf")
        //EventBus.getDefault().register(SoundPlayer)
    }

    companion object
    {
        lateinit var appComponent: AppComponent

    }
}