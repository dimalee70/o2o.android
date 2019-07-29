package kz.dragau.larek.di.modules

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.bumptech.glide.load.model.GlideUrl
import kz.dragau.larek.Constants
import java.io.InputStream

/*
@GlideModule
class AppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
         val client = OkHttpClient.Builder()
             .connectTimeout(Constants.connectTimeout, TimeUnit.SECONDS)
             .writeTimeout(Constants.writeTimeout, TimeUnit.SECONDS)
             .readTimeout(Constants.readTimeout, TimeUnit.SECONDS)
             .build()

         val factory = OkHttpUrlLoader.Factory(client)

         glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}*/