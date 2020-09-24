package com.esmac.android.pandora.util.extention

import android.content.res.Resources
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Created by Hữu Nguyễn on 05/05/2020.
 * Email: huuntt1905@gmail.com.
 */

fun Double.rounded(): Double {
    return (this * 100.0).roundToInt() / 100.0
}

fun Double.roundUpToHalf(): Double {
    return ceil(this * 2) / 2
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.toHumanReadable: String
    get() = NumberFormat.getIntegerInstance().format(this)

val String.toHumanReadable: String
    get() = NumberFormat.getIntegerInstance().format(this)

val Long.toHumanReadable: String
    get() = NumberFormat.getIntegerInstance().format(this)

fun String.toVndCurrency(value: Any?) : String {
    val formatter = DecimalFormat("###,###,###.#")
    val millionFormatter = DecimalFormat("###,###,###")
    return if (value != null) when (value) {
        is Double -> {
            when {
                value > 999999999 -> "${formatter.format(value / 1000000000)} tỉ"
                value >  999999 -> "${formatter.format(value / 1000000.0)} triệu"
                value > 999 -> "${millionFormatter.format(value / 1000)}k"
                else -> "${value.toInt()}"
            }
        }
        is Long -> {
            when {
                value > 999999999 -> "${formatter.format(value / 1000000000)} tỉ"
                value > 999999 -> "${formatter.format(value / 1000000.0)} triệu"
                value > 999 -> "${millionFormatter.format(value / 1000)}k"
                else -> "$value"
            }
        }
        is String -> {
            try {
                toVndCurrency(value.toDouble())
            } catch (e: Exception) {
                "$value"
            }
        }
        else -> "$value"
    } else "0"
}
