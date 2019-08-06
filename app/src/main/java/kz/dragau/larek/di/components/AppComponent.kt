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
import kz.dragau.larek.models.db.Db
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.presentation.presenter.MainAppPresenter
import kz.dragau.larek.presentation.presenter.confirm.ConfirmCodePresenter
import kz.dragau.larek.presentation.presenter.login.PhoneNumberPresenter
import kz.dragau.larek.presentation.presenter.login.SmsCodePresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.activity.MainAppActivity
import kz.dragau.larek.ui.fragment.confirm.ConfirmCodeFragment
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment

@Singleton
@CustomApplicationScope
@Component(modules = [ApplicationModule::class, NavigationModule::class, ServiceUtilModule::class, RoomModule::class])
interface AppComponent {
    @ApplicationContext
    fun context(): Context

    @ApplicationContext
    fun instance(): App

    fun getServiceUtil(): ApiManager

    fun glideComponentBuilder(): GlideComponent.Builder

    fun userDao(): UserDao

    fun getDb(): Db

    //fun inject(loginModule: LoginModule)
    //fun inject(loginProcessPresenter: LoginProcessPresenter)

    fun inject(activity: BaseActivity)
    fun inject(activity: MainAppActivity)
    fun inject(fragment: PhoneNumberFragment)
    fun inject(fragment: SmsCodeFragment)
    fun inject(fragmnet: ConfirmCodeFragment)


    fun inject(presenter: MainAppPresenter)
    fun inject(presenter: PhoneNumberPresenter)
    fun inject(presenter: SmsCodePresenter)
    fun inject(presenter: ConfirmCodePresenter)

    //fun getApi(): ApiManager
    //fun getGlide(): GlideApp

    //fun inject (glide: AppGlideModule)
}