package com.esmac.android.pandora.data.remote.response.base

/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
