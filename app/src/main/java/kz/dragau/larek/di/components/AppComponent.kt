package kz.dragau.larek.di.components

import android.content.Context
import com.google.android.gms.common.data.DataHolder
import com.google.android.gms.maps.MapFragment
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
import kz.dragau.larek.presentation.presenter.registration.RegistrationPresenter
import kz.dragau.larek.presentation.presenter.map.LocationMapPresenter
import kz.dragau.larek.presentation.presenter.product.ProductRegisterPresenter
import kz.dragau.larek.ui.activity.BaseActivity
import kz.dragau.larek.ui.activity.MainAppActivity
import kz.dragau.larek.ui.activity.product.ProductActivity
import kz.dragau.larek.ui.fragment.confirm.ConfirmCodeFragment
import kz.dragau.larek.ui.fragment.login.PhoneNumberFragment
import kz.dragau.larek.ui.fragment.login.SmsCodeFragment
import kz.dragau.larek.ui.fragment.registration.RegistrationFragment
import kz.dragau.larek.ui.fragment.map.LocationMapFragment
import kz.dragau.larek.ui.fragment.product.ProductRegisterFragment

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
    fun inject(activity: ProductActivity)
    fun inject(fragment: PhoneNumberFragment)
    fun inject(fragment: SmsCodeFragment)
    fun inject(fragmnet: ConfirmCodeFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: LocationMapFragment)
    fun inject(fragment: ProductRegisterFragment)

    fun inject(presenter: MainAppPresenter)
    fun inject(presenter: PhoneNumberPresenter)
    fun inject(presenter: SmsCodePresenter)
    fun inject(presenter: ConfirmCodePresenter)
    fun inject(presenter: RegistrationPresenter)
    fun inject(presenter: LocationMapPresenter)
    fun inject(presenter: ProductRegisterPresenter)

    //fun getApi(): ApiManager
    //fun getGlide(): GlideApp

    //fun inject (glide: AppGlideModule)
}