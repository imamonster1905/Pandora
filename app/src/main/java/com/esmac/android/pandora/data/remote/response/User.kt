package com.esmac.android.pandora.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hữu Nguyễn on 13/03/2020.
 * Email: huuntt1905@gmail.com.
 */
@Entity
data class User(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val avatar: String?,
    val cover: String?,
    val email: String?,
    val phone_number: String?,
    val referral_code: String?,
    val created_at: String?
)