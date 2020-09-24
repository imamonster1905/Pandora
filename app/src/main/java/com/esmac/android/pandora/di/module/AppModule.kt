package com.esmac.android.pandora.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.esmac.android.pandora.util.di.scheduler.AndroidSchedulerProvider
import com.esmac.android.pandora.data.local.preferance.AppPrefManger
import com.esmac.android.pandora.data.local.preferance.PrefManager
import com.esmac.android.pandora.di.ApplicationScope
import com.esmac.android.pandora.util.commom.Constants
import com.esmac.android.pandora.util.di.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Hữu Nguyễn on 01/06/2020.
 * Email: huuntt1905@gmail.com.
 */
@Module
class AppModule {
    @Provides
    @ApplicationScope
    fun bindSchedulerProvider(androidSchedulerProvider: AndroidSchedulerProvider): SchedulerProvider {
        return androidSchedulerProvider
    }

    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun provideSharedPreference(context: Context) : SharedPreferences =
        context.getSharedPreferences(Constants.AppPref.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @ApplicationScope
    fun provideAppPrefManger(sharedPreferences: SharedPreferences) : PrefManager =
        AppPrefManger(sharedPreferences)
}