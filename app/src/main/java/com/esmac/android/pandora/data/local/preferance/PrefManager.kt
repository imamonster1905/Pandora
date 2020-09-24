package com.esmac.android.pandora.data.local.preferance

interface PrefManager {
    fun getToken() : String?
    fun setToken(token: String)

    fun getDeviceToken(): String?
    fun setDeviceToken(accessToken: String)

    fun isLogged(): Boolean
    fun clear()
}