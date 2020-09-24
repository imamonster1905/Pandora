package com.esmac.android.pandora.ui.splash

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.esmac.android.pandora.di.module.viewmodel.ViewModelFactory
import com.esmac.android.pandora.data.local.preferance.PrefManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.*
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var prefManager: PrefManager

    lateinit var viewModel: SplashViewModel

    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        viewModel.isUserLogged.observe(this, Observer { isLogged ->
            activityScope.launch {
                delay(1000)
//                if (isLogged) {
//                    MainActivity.start(this@SplashActivity)
//                } else {
//                    AuthActivity.start(this@SplashActivity)
//                }
            }
        })

    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
