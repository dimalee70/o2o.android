package kz.dragau.larek.di.components

import android.content.Context
import dagger.Component
import kz.dragau.larek.ApplicationController
import kz.dragau.larek.di.modules.AppModule
import kz.dragau.larek.di.modules.ContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ContextModule::class))
interface AppComponent {
    fun getContext(): Context
    fun inject(app: ApplicationController)
    //fun inject(loginModule: LoginModule)
    //fun inject(loginProcessPresenter: LoginProcessPresenter)
}