package com.esmac.android.pandora.data.remote.request

/**
 * Created by Hữu Nguyễn on 19/03/2020.
 * Email: huuntt1905@gmail.com.
 */
data class RegisterRequest(
    val name: String?,
    val email: String?,
    val phone_number: String?,
    val address: String?,
    val postal_code: String?,
    val password: String?,
    val password_confirmation: String?,
    val place_id: Long?
)