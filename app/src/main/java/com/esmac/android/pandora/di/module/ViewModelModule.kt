package com.esmac.android.pandora.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esmac.android.pandora.di.module.viewmodel.ViewModelFactory
import com.esmac.android.pandora.di.module.viewmodel.ViewModelKey
import com.esmac.android.pandora.di.ApplicationScope
import com.esmac.android.pandora.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @ApplicationScope
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
