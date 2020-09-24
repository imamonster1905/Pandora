package com.esmac.android.pandora

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.esmac.android.pandora.data.local.preferance.PrefManager
import com.esmac.android.pandora.di.DaggerAppComponent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Hữu Nguyễn on 01/06/2020.
 * Email: huuntt1905@gmail.com.
 */
@Suppress("SpellCheckingInspection")
open class PandoraApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var prefManager: PrefManager

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()

        initFirebase()
    }

    @SuppressLint("HardwareIds")
    private fun initFirebase() {
        try {

            FirebaseMessaging.getInstance().isAutoInitEnabled = true
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
//                        showErrorToast("getInstanceId failed ${task.exception}")
//                        logE(TAG,"getInstanceId failed ${task.exception}")
                        return@OnCompleteListener
                    }
                    val token = task.result?.token
//                    logD(TAG, "token: $token")

                    val deviceId = Settings.Secure.getString(this.contentResolver,Settings.Secure.ANDROID_ID)
//                    logD(TAG,"deviceId: $deviceId")

                    token?.let { prefManager.setDeviceToken(it) }
//                    prefManager.setDeviceId(deviceId)
                })
        } catch (e: IOException) {
//            showErrorToast("Cannot get device Token")
        }
    }
}
