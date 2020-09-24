package com.esmac.android.pandora.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Resource
import com.esmac.android.pandora.util.extention.TAG
import com.esmac.android.pandora.util.extention.logE
import com.esmac.android.pandora.util.obj.Event
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
open class BaseViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    val isLoading = MutableLiveData<Boolean>()
    val expireToken = MutableLiveData(Event(false))
    val httpException = MutableLiveData<Event<HttpException>>()
    val networkNotAvailable = MutableLiveData<Event<Boolean>>()
    val internalServerException = MutableLiveData<Event<String>>()
    val socketTimeoutException = MutableLiveData<Event<String>>()
    val ioException = MutableLiveData<Event<String>>()
    val unknownError = MutableLiveData<Event<String>>()

//    var unreadNotificationResponse = MutableLiveData<Resource<UnreadNotification>>()

    open fun showLoading() {
        isLoading.value = true
    }

    open fun hideLoading() {
        isLoading.value = false
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    open suspend fun onLoadFail(throwable: Throwable) {
        onError(throwable)
    }

    /**
     * Handle exception
     * */
    open fun onError(throwable: Throwable) {
        logE(TAG,"${throwable.localizedMessage} \n ${throwable.message}")
        hideLoading()
        when (throwable) {
            is UnknownHostException -> {
                networkNotAvailable()
            }
            is HttpException -> {
                when (throwable.code()) {
                    401, 403 -> {
                        onExpireToken()
                    }
                    422 -> {
                        onHttpException(throwable)
                    }
                    500 -> {
                        throwable.response()?.errorBody()?.let { getErrorMessage(it) }
                    }
                    else -> onHttpException(throwable) /*400, 404, 502*/
                }
            }
            is SocketTimeoutException -> {
                onSocketTimeout()
                logE(
                    TAG,
                    "onError: Socket Timeout Exception"
                )
            }
            is IOException -> {
                onIOException()
                logE(TAG, "onError: IO Exception")
            }
            else -> {
                onUnknowError("${throwable.message}")
                logE(
                    TAG,
                    "onError: ${throwable.message}"
                )
            }
        }
    }

    private fun onHttpException(throwable: HttpException) {
        httpException.value = Event(throwable)
    }

    fun onExpireToken() {
        expireToken.value = Event(true)
    }

    private fun networkNotAvailable() {
        networkNotAvailable.value = Event(true)
    }

    private fun onUnknowError(error: String?) {
        error?.let {
            unknownError.value = Event(error)
        }
    }

    private fun onSocketTimeout() {
        socketTimeoutException.value =
            Event("Socket Timeout Exception")
    }

    private fun onIOException() {
        ioException.value = Event("IO Exception")
    }

    fun getErrorMessage(responseBody: ResponseBody): String? {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            if (jsonObject.has("msg"))
                jsonObject.getString("msg")
            else
                jsonObject.getString("message")
        } catch (e: Exception) {
            e.message
        }
    }
}