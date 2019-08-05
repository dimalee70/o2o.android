package kz.dragau.larek.di.components

import android.content.Context
import com.google.android.gms.common.data.DataHolder
import dagger.Component
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import javax.inject.Singleton
import kz.dragau.larek.di.ApplicationContext
import kz.dragau.larek.di.CustomApplicationScope
import kz.dragau.larek.di.modules.*
import kz.dragau.larek.presentation.presenter.MainAppPresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.activity.MainAppActivity

@Singleton
@CustomApplicationScope
@Component(modules = [ApplicationModule::class, NavigationModule::class, ServiceUtilModule::class])
interface AppComponent {
    @ApplicationContext
    fun context(): Context

    @ApplicationContext
    fun instance(): App

    fun getServiceUtil(): ApiManager

    fun glideComponentBuilder(): GlideComponent.Builder
    //fun inject(loginModule: LoginModule)
    //fun inject(loginProcessPresenter: LoginProcessPresenter)

    fun inject(activity: BaseActivity)

    fun inject(activity: MainAppActivity)


    fun inject(presenter: MainAppPresenter)

    //fun getApi(): ApiManager
    //fun getGlide(): GlideApp

    //fun inject (glide: AppGlideModule)
}