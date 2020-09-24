package com.esmac.android.pandora.util.preferences

import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by Hữu Nguyễn on 10/07/2020.
 * Email: huuntt1905@gmail.com.
 */
object Preferences {
    val THEME_PREFERENCE = mapOf(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM to "R.string.system_default",
        AppCompatDelegate.MODE_NIGHT_YES to "R.string.dark",
        AppCompatDelegate.MODE_NIGHT_NO to "R.string.light"
    )
}