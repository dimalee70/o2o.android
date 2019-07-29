package kz.dragau.larek.di.modules

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val mContext: Context) {

    @Provides
    @Singleton
    @NonNull
    fun provideContext(): Context {
        return mContext
    }
}