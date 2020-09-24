package com.esmac.android.pandora.data.remote.request

/**
 * Created by Hữu Nguyễn on 19/03/2020.
 * Email: huuntt1905@gmail.com.
 */
data class LoginRequest(
    val username: String?,
    val password: String?,
    val device_type: String?,
    val device_token: String?
)