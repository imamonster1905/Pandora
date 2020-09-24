package com.esmac.android.pandora.di

import android.app.Application
import com.esmac.android.pandora.PandoraApplication
import com.esmac.android.pandora.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ViewModelModule::class,
//        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun inject(app: PandoraApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}