package com.esmac.android.pandora.util.extention

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.format.DateUtils
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.esmac.android.pandora.util.commom.MyDateUtil
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hữu Nguyễn on 13/07/2020.
 * Email: huuntt1905@gmail.com.
 */

const val EMPTY_DATE_TIME = "--:--"

@BindingAdapter("parseHtml")
fun TextView.setHtml(htmlContent: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        htmlContent?.let {
            text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        }
    } else {
        htmlContent?.let {
            text = Html.fromHtml(it)
        }
    }
}

@BindingAdapter("android:text")
fun TextView.setText(content: Any?) {
    text = when (content) {
        is Double, is Long, is Int, is Float -> "$content"
        is String -> content
        else -> ""
    }
}

@BindingAdapter("textDouble")
fun TextView.setTextDouble(number: Double?) {
    text = number?.rounded()?.toString() ?: "0"
}

@BindingAdapter("ddMMyyyy")
fun TextView.to_ddMMyyyy(content: String?) {
    text = content?.let { MyDateUtil.formatDateFromServerToShortStamp(content) } ?: EMPTY_DATE_TIME
}

@BindingAdapter("hhmmddMMyyyy")
fun TextView.to_hhmmddMMyyyy(content: String?) {
    text = content?.let { MyDateUtil.formatDateFromServerToShortStamp(content) } ?: EMPTY_DATE_TIME
}

@BindingAdapter("toMoment")
fun TextView.toMoment(content: String?) {
    text = content?.let {
        try {
            val inputFormat = SimpleDateFormat(MyDateUtil.yyyy_MM_dd__HH_mm_ss, Locale.getDefault())
            val date: Date = inputFormat.parse(it)

            DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } catch (e: ParseException) {
            //try another constant Stamp
            try {
                val inputFormat = SimpleDateFormat(MyDateUtil.DATE_FORMAT_12, Locale.getDefault())
                val date: Date = inputFormat.parse(it)

                DateUtils.getRelativeTimeSpanString(
                    date.time,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS
                ).toString()
            } catch (e: ParseException) {
                content
            }
        }
    }
        ?: EMPTY_DATE_TIME
}

@SuppressLint("SetTextI18n")
@BindingAdapter(
    value = ["textFormatNumber", "prefixText", "suffixText"],
    requireAll = false
)
fun TextView.setTextFormatNumber(value: Any?, prefixText: String?, suffixText: String?) {
    if (value == null)
        text = "0"
    else
        when (value) {
            is Number -> text = DecimalFormat("###,###,###").format(value)
            is Double -> text = DecimalFormat("###,###,###").format(value.rounded())
            is String -> text = try {
                DecimalFormat("###,###,###").format(value.toInt())
            } catch (e: java.lang.Exception) {
                "$value"
            }
        }
    if (prefixText != null)
        text = "$prefixText $text"
    if (suffixText != null)
        text = "$text $suffixText"
}

@SuppressLint("SetTextI18n")
@BindingAdapter(
    value = ["textFormatNumber", "prefixText", "suffixText"],
    requireAll = false
)
fun CheckBox.setTextFormatNumber(value: Any?, prefixText: String?, suffixText: String?) {
    text = if (value != null) DecimalFormat("###,###,###").format(value)
    else "0"
    if (prefixText != null)
        text = "$prefixText $text"
    if (suffixText != null)
        text = "$text $suffixText"
}

@SuppressLint("SetTextI18n")
@BindingAdapter(
    value = ["setCurrency", "applySymbol", "setCurrencyUnderLine"],
    requireAll = false
)
fun TextView.setCurrency(value: Any?, applySymbol: String?, isUnderLine: Boolean) {
    val formatter = DecimalFormat("###,###,###.#")
    val millionFormatter = DecimalFormat("###,###,###")
    if (value != null) when (value) {
        is Double -> {
            text = when {
                value > 999999999 -> "${formatter.format(value / 1000000000)} tỉ"
                value >  999999 -> "${formatter.format(value / 1000000.0)} triệu"
                value > 999 -> "${millionFormatter.format(value / 1000)}k"
                else -> "${value.toInt()}"
            }
        }
        is Long -> {
            text = when {
                value > 999999999 -> "${formatter.format(value / 1000000000)} tỉ"
                value > 999999 -> "${formatter.format(value / 1000000.0)} triệu"
                value > 999 -> "${millionFormatter.format(value / 1000)}k"
                else -> "$value"
            }
        }
        is String -> {
            try {
//                setCurrency(value.toDouble(), this.context.resources.getString(R.string.txt_default_symbol_current), false)
                setCurrency(value.toDouble(), null, false)
            } catch (e: Exception) {
                text = "$value"
            }
        }
    } else text = "0"

    if (applySymbol.isNullOrEmpty().not()) {
        text = text.toString().plus(" ").plus("$applySymbol")
    }
    if (isUnderLine) {
        this.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("strikeThrough")
fun TextView.setStrikeThrough(shouldStrike: Boolean) {
    if (shouldStrike)
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}