package com.esmac.android.pandora.data.local.preferance

import android.content.SharedPreferences
import javax.inject.Inject

class AppPrefManger @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrefManager {
    companion object {
        const val APP_PREF_TOKEN = "app_pref_token"
        const val APP_PREF_MY_ID = "app_pref_my_id"
        const val APP_PREF_DEVICE_ID = "app_pref_device_id"
        const val APP_PREF_ACCESS_TOKEN = "app_pref_access_token"
        const val APP_PREF_CUSTOMER_SERVICE_FEE = "customer_service_fee"
        const val APP_PREF_FEE247 = "fee247"
        const val APP_PREF_AMOUNT_SUBSCRIBER = "amount_subscriber"
    }

    override fun getToken(): String? {
        return sharedPreferences.get(APP_PREF_TOKEN, "")
    }

    override fun setToken(token: String) {
        sharedPreferences.put(APP_PREF_TOKEN, token)
    }

    override fun getDeviceToken(): String? {
        return sharedPreferences.get(APP_PREF_ACCESS_TOKEN, "")
    }

    override fun setDeviceToken(accessToken: String) {
        sharedPreferences.put(APP_PREF_ACCESS_TOKEN, accessToken)
    }

    override fun isLogged(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}