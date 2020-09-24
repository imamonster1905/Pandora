package com.esmac.android.pandora.ui.splash

import androidx.lifecycle.MutableLiveData
import com.esmac.android.pandora.data.local.preferance.PrefManager
import com.esmac.android.pandora.ui.base.BaseViewModel
import com.esmac.android.pandora.util.di.scheduler.SchedulerProvider
import com.esmac.android.pandora.util.extention.logD
import javax.inject.Inject

/**
 * Created by Hữu Nguyễn on 18/03/2020.
 * Email: huuntt1905@gmail.com.
 */
class SplashViewModel @Inject constructor(
    private val prefManger: PrefManager,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var isUserLogged = MutableLiveData<Boolean>().apply {
        value = prefManger.isLogged()
        logD("is User Logged", "$value")
    }
}