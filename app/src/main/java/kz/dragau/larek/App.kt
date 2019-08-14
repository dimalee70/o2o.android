package kz.dragau.larek

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.dragau.larek.di.components.AppComponent
import kz.dragau.larek.di.components.DaggerAppComponent
import org.greenrobot.eventbus.EventBus
import com.crashlytics.android.core.CrashlyticsCore
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposable
import kz.dragau.larek.di.modules.ApplicationModule
import kz.dragau.larek.di.modules.RoomModule
import kz.dragau.larek.di.modules.WSocketModule
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber.DebugTree
import timber.log.Timber
import android.util.StatsLog.logEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import android.system.Os.listen
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.schedulers.Schedulers
import com.navin.flintstones.rxwebsocket.RxWebsocket
import android.R.id.message
import android.R.id







class App : MultiDexApplication() {
    private var cicerone: Cicerone<Router>? = null

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .wSocketModule(WSocketModule())
            .build()

        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()
        Fabric.with(this, crashlyticsKit)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

       /*logEvents()

        disposable = App.appComponent.getWSocket()
            .connect()
            .flatMap {
                    open -> open.client().send("Hello")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {event ->
                    when (event) {
                        is RxWebsocket.Open -> {
                            Timber.i("CONNECTED")
                        }
                    }

                },
                {e ->
                    Timber.e(e)
                }
            )*/
    }

    private fun logEvents() {
        App.appComponent.getWSocket().eventStream()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext({ event ->
                when (event) {
                    is RxWebsocket.Open -> {
                        Timber.i("CONNECTED")
                    }
                    is RxWebsocket.Closed -> {
                        Timber.i("DISCONNECTED")
                    }
                    is RxWebsocket.QueuedMessage<*> -> {
                        Timber.i("[MESSAGE QUEUED]:" + (event as RxWebsocket.QueuedMessage<*>).message()!!.toString())
                    }
                    is RxWebsocket.Message -> try {
                        Timber.i("[MESSAGE RECEIVED]:" + (event as RxWebsocket.Message).data()!!.toString())
                        //Timber.i("[DE-SERIALIZED MESSAGE RECEIVED]:" + (event as RxWebsocket.Message).data(SampleDataModel::class.java).toString())
                        /*log(
                            String.format(
                                "[DE-SERIALIZED MESSAGE RECEIVED][id]:%d",
                                (event as RxWebsocket.Message).data(SampleDataModel::class.java).id()
                            )
                        )
                        log(
                            String.format(
                                "[DE-SERIALIZED MESSAGE RECEIVED][message]:%s",
                                (event as RxWebsocket.Message).data(SampleDataModel::class.java).message()
                            )
                        )*/
                    } catch (throwable: Throwable) {
                        Timber.i("[MESSAGE RECEIVED]:" + (event as RxWebsocket.Message).data()!!.toString())
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .subscribe(emptyConsumer())
    }

    companion object
    {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }
}