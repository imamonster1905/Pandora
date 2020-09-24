package com.esmac.android.pandora.data.remote.response.base

/**
 * Created by Hữu Nguyễn on 13/03/2020.
 * Email: huuntt1905@gmail.com.
 */
data class BaseResponse<T>(
    val status: Int?,
    val msg: String?,
    val data: T?,
    val errors: Any?
)