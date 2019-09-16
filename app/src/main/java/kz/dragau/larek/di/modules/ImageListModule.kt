package kz.dragau.larek.di.modules

import android.media.Image
import dagger.Module
import dagger.Provides
import kz.dragau.larek.models.objects.Images
import javax.inject.Singleton

@Module
class ImageListModule {
    private var images: Images = Images()

    @Provides
    @Singleton
    fun provideImages(): Images{
        return images
    }
}