package kz.dragau.larek.di.modules

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import kz.dragau.larek.App
import kz.dragau.larek.di.ApplicationContext
import javax.inject.Singleton


@Module
class ApplicationModule(private val app: App) {
    @Provides
    @Singleton
    @ApplicationContext
    fun context() = app.applicationContext

    @Provides
    @Singleton
    @ApplicationContext
    fun instance() = app

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

}