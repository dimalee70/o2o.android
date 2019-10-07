package kz.dragau.larek.di.modules

import dagger.Module
import dagger.Provides
import kz.dragau.larek.api.requests.ProductRegisterViewModel
import javax.inject.Singleton

@Module
class ProductAddModule {

    private var productRegisterViewModel: ProductRegisterViewModel = ProductRegisterViewModel()

    @Provides
    @Singleton
    fun provideProductRegisterViewModel(): ProductRegisterViewModel{
        return productRegisterViewModel
    }
}