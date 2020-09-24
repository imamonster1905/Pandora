package com.esmac.android.pandora.data.remote.response

import android.os.Parcelable
import com.esmac.android.pandora.util.commom.MyDateUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: Long?,
    val title: String? = null,
    val body: String? = null,
    val collapse_key: String? = null,
    val resource_id: Long? = null,
    var read_at: String? = null,
    val imageUrl: String? = null,
    val user_id: Long? = null,
    val system_notification_id: Long? = null,
    val created_at: String? = null,
    val updated_at: String? = null
) : Parcelable {

    val isRead: Boolean
        get() = read_at != null

    val momentTime : String?
        get() = MyDateUtil.getMoment(created_at)
}