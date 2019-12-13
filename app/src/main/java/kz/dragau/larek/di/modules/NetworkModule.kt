package kz.dragau.larek.di.modules

import android.content.Context
import dagger.Module
import okhttp3.OkHttpClient
import dagger.Provides
import kz.dragau.larek.Constants
import kz.dragau.larek.api.TokenInterceptor
import kz.dragau.larek.di.ApplicationContext
import kz.dragau.larek.di.CustomApplicationScope
import kz.dragau.larek.models.shared.DataHolder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit


@Module(includes = [ApplicationModule::class])
class NetworkModule {

//    @Provides
//    @Singleton
//    fun provideOkhttp(tokenInterceptor: TokenInterceptor): OkHttpClient{
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val okHttpBuilder =
//            OkHttpClient().newBuilder().addInterceptor(interceptor)
//                .addInterceptor(tokenInterceptor)
//        return okHttpBuilder.build()
//    }
    @Provides
    @CustomApplicationScope
    fun getInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
                message -> Timber.v(message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @CustomApplicationScope
    fun getSessionInterceptor(): JwtInterceptor {
        val interceptor = JwtInterceptor()
        return interceptor
    }

    @Provides
    @CustomApplicationScope
    fun getCache(cacheFile: File): Cache {
        return Cache(cacheFile, 100 * 1000 * 1000)
    }

    @Provides
    @CustomApplicationScope
    fun getFile(@ApplicationContext context: Context): File {
        val file = File(context.filesDir, "cache_dir")
        if (!file.exists())
            file.mkdirs()
        return file
    }

    @Provides
    @CustomApplicationScope
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor, jwtInterceptor: JwtInterceptor, cache: Cache, tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(Constants.writeTimeout, TimeUnit.SECONDS)
            .readTimeout(Constants.readTimeout, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(jwtInterceptor)
            .build()
    }
}


class JwtInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header("No-Authentication")==null){
            val token = DataHolder.jwt

            if(token != null && token.isNotEmpty())
            {
                val finalToken = "Bearer $token"
                request = request.newBuilder()
                    .addHeader("Authorization",finalToken)
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
        }
        return chain.proceed(request)
    }

}