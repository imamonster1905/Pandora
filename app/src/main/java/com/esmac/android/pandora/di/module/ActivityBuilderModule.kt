package com.esmac.android.pandora.di.module

import com.esmac.android.pandora.ui.MainActivity
import com.esmac.android.pandora.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Hữu Nguyễn on 01/06/2020.
 * Email: huuntt1905@gmail.com.
 */

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}