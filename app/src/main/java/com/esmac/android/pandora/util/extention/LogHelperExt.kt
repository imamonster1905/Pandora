package com.esmac.android.pandora.util.extention

import android.util.Log
import com.esmac.android.pandora.BuildConfig

/**
 * Created by Hữu Nguyễn on 23/03/2020.
 * Email: huuntt1905@gmail.com.
 */

const val APP_TAG = "DICAR-TAG"
const val START_CHAR_LOG = "<--------  "
const val END_CHAR_LOG = "  -------->"

val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            if (name.length <= 23) name else name.substring(0, 23)// first 23 chars
        } else {
            val name = javaClass.name
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)// last 23 chars
        }
    }
fun logUnTag(message: Any): String = "$START_CHAR_LOG$message$END_CHAR_LOG"
fun logWithTag(tag: String, message: Any): String = "$START_CHAR_LOG$tag : $message$END_CHAR_LOG"

fun logD(message: Any){
    if (BuildConfig.DEBUG)
        Log.d(
            APP_TAG,
            logUnTag(message)
        )
}

fun logD(tag: String, message: Any){
    if (BuildConfig.DEBUG)
        Log.d(
            APP_TAG,
            logWithTag(tag, message)
        )
}

fun logE(message: Any){
    if (BuildConfig.DEBUG)
        Log.e(
            APP_TAG,
            logUnTag(message)
        )
}

fun logE(tag: String, message: Any){
    if (BuildConfig.DEBUG)
        Log.e(
            APP_TAG,
            logWithTag(tag, message)
        )
}

fun logI(message: Any){
    if (BuildConfig.DEBUG)
        Log.i(
            APP_TAG,
            logUnTag(message)
        )
}

fun logI(tag: String, message: Any){
    if (BuildConfig.DEBUG)
        Log.i(
            APP_TAG,
            logWithTag(tag, message)
        )
}
