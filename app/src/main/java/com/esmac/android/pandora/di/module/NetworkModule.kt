package com.esmac.android.pandora.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.esmac.android.pandora.network.ApiService
import com.esmac.android.pandora.BuildConfig
import com.esmac.android.pandora.data.local.preferance.PrefManager
import com.esmac.android.pandora.di.ApplicationScope
import com.esmac.android.pandora.util.di.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {

    companion object {
        const val CONNECT_TIMEOUT: Long = 30
        const val READ_TIMEOUT: Long = 30
        const val WRITE_TIMEOUT: Long = 30
    }

    @Provides
    @ApplicationScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.HEADERS
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @ApplicationScope
    fun provideCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 5 MB
        val cacheDir = context.cacheDir
        return Cache(cacheDir, cacheSize.toLong())
    }

    @Provides
    @ApplicationScope
    fun provideInterceptor(
        prefManager: PrefManager
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Accept", "application/json")
            requestBuilder.header("Authorization", "Bearer ${prefManager.getToken()}")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
            .cache(cache)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(
        httpClient: OkHttpClient,
        schedulerProvider: SchedulerProvider
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io()))
            .client(httpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @ApplicationScope
    fun provideConnectivityManager(context: Application): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}
