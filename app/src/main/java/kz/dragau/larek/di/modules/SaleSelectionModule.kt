package kz.dragau.larek.di.modules

import dagger.Module
import dagger.Provides
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import javax.inject.Singleton

@Module
class SaleSelectionModule{

    private var saleSelector: SaleSelector = SaleSelector()

    @Provides
    @Singleton
    fun provideSaleSelection(): SaleSelector{
        return  saleSelector
    }
}