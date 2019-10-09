package kz.dragau.larek.di.modules

import androidx.databinding.ObservableArrayList
import dagger.Module
import dagger.Provides
import kz.dragau.larek.models.objects.Customs
import javax.inject.Singleton

@Module
class CustomsModule {
    private  var customs = ObservableArrayList<Customs>()

    @Provides
    @Singleton
    fun provideCustoms(): ObservableArrayList<Customs>{
        return  customs
    }
}