package kz.dragau.larek.di.modules

import androidx.databinding.ObservableArrayList
import dagger.Module
import dagger.Provides
import kz.dragau.larek.models.objects.Customs
import kz.dragau.larek.models.objects.OrdersByOutletResult
import javax.inject.Singleton

@Module
class CustomsModule {
    private  var customs = ObservableArrayList<OrdersByOutletResult>()

    @Provides
    @Singleton
    fun provideCustoms(): ObservableArrayList<OrdersByOutletResult>{
        return  customs
    }
}