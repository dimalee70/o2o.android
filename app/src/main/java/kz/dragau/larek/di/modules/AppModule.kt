package kz.dragau.larek.di.modules

import dagger.Module
import dagger.Provides
import kz.dragau.larek.ApplicationController

@Module
class AppModule(val app: ApplicationController)
{
    @Provides
    fun provideApp(): ApplicationController = app

}