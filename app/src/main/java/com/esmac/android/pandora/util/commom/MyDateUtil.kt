package com.esmac.android.pandora.util.commom

import android.text.format.DateFormat
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hữu Nguyễn on 01/06/2020.
 * Email: huuntt1905@gmail.com.
 */
object MyDateUtil {

    private const val TAG = "MyDateUtil"

    const val DATE_FORMAT_1 = "hh:mm a"
    const val DATE_FORMAT_2 = "h:mm a"
    const val DATE_FORMAT_3 = "yyyy-MM-dd"
    const val DATE_FORMAT_4 = "yyyy-MM-dd"
    const val DATE_FORMAT_5 = "dd MMMM yyyy"
    const val DATE_FORMAT_6 = "dd MMMM yyyy zzzz"
    const val DATE_FORMAT_7 = "EEE, MMM d, ''yy"
    const val DATE_FORMAT_9 = "h:mm a dd MMMM yyyy"
    const val DATE_FORMAT_10 = "K:mm a, z"
    const val DATE_FORMAT_11 = "hh 'o''clock' a, zzzz"
    const val DATE_FORMAT_12 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_FORMAT_13 = "E, dd MMM yyyy HH:mm:ss z"
    const val DATE_FORMAT_14 = "yyyy.MM.dd G 'at' HH:mm:ss z"
    const val DATE_FORMAT_15 = "yyyyy.MMMMM.dd GGG hh:mm aaa"
    const val DATE_FORMAT_16 = "EEE, d MMM yyyy HH:mm:ss Z"
    const val DATE_FORMAT_17 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val DATE_FORMAT_18 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val OUTPUT_DATE_FORMAT = "HH:mm:ss - MM/dd/yyyy"
    const val OUTPUT_DATE_FORMAT2 = "dd/MM/yyyy, HH:mm:ss aa"
    const val OUTPUT_DATE_FORMAT22 = "dd/MM/yyyy'-'HH:mm"
    const val OUTPUT_DATE_FORMAT3 = "HH'h':mm, dd/MM"
    const val OUTPUT_DATE_FORMAT4 = "HH'h':mm, dd/MM/yyyy"
    const val OUTPUT_DATE_FORMAT_DAY_OF_WEEK = "EEEE"
    const val OUTPUT_DATE_FORMAT_DAY_OF_MONTH = "dd"
    const val OUTPUT_DATE_FORMAT_MONTH_YEAR = "MM/yyyy"
    const val OUTPUT_DATE_FORMAT_HOUR_MINUTE = "HH 'giờ' mm"
    const val OUTPUT_DATE_FORMAT_SHORT = "dd/MM/yyyy"

    const val yyyy_MM_dd__HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val HH_mm__dd_MM_yyyy = "HH'h':mm dd/MM/yyyy"

    fun getCurrentDateTime(): String? {
        val dateFormat =
            SimpleDateFormat(yyyy_MM_dd__HH_mm_ss)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }

    fun getCurrentDate(): String? {
        val dateFormat =
            SimpleDateFormat(DATE_FORMAT_1)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }

    fun getCurrentTime(): String? {
        val dateFormat =
            SimpleDateFormat(DATE_FORMAT_1)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }

    /**
     * @param time        in milliseconds (Timestamp)
     * @param mDateFormat SimpleDateFormat
     * @return
     */
    fun getDateTimeFromTimeStamp(
        time: Long?,
        mDateFormat: String?
    ): String? {
        val dateFormat = SimpleDateFormat(mDateFormat)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = Date(time!!)
        return dateFormat.format(dateTime)
    }

    /**
     * Get Timestamp from date and time
     *
     * @param mDateTime   datetime String
     * @param mDateFormat Date Format
     * @return
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun getTimeStampFromDateTime(
        mDateTime: String?,
        mDateFormat: String?
    ): Long {
        val dateFormat = SimpleDateFormat(mDateFormat)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse(mDateTime)
        return date.time
    }

    /**
     * Return  datetime String from date object
     *
     * @param mDateFormat format of date
     * @param date        date object that you want to parse
     * @return
     */
    fun formatDateTimeFromDate(
        mDateFormat: String?,
        date: Date?
    ): String? {
        return if (date == null) {
            null
        } else DateFormat.format(mDateFormat, date).toString()
    }

    /**
     * Convert one date format string  to another date format string in android
     *
     * @param inputDateFormat  Input SimpleDateFormat
     * @param outputDateFormat Output SimpleDateFormat
     * @param inputDate        input Date String
     * @return
     * @throws ParseException
     */
    fun formatDateFromDateString(
        inputDateFormat: String?,
        outputDateFormat: String?,
        inputDate: String?
    ): String? {
        return try {
            val mParsedDate: Date
            val mOutputDateString: String
            val mInputDateFormat =
                SimpleDateFormat(inputDateFormat, Locale.getDefault())
            val mOutputDateFormat =
                SimpleDateFormat(outputDateFormat, Locale.getDefault())
            mParsedDate = mInputDateFormat.parse(inputDate)
            mOutputDateString = mOutputDateFormat.format(mParsedDate)
            mOutputDateString
        } catch (e: ParseException) {
            inputDate
        }
    }


    fun formatDateFromDateStringShort(yyyyMMdd: String?): String? {
        return formatDateFromDateString(
            DATE_FORMAT_3,
            OUTPUT_DATE_FORMAT_SHORT,
            yyyyMMdd
        )
    }

    fun formatDateFromDateStringShort2(ddMMyyyy: String?): String? {
        return formatDateFromDateString(
            DATE_FORMAT_4,
            OUTPUT_DATE_FORMAT_SHORT,
            ddMMyyyy
        )
    }

    fun formatDateFromServerToLongStamp(yyyyMMdd: String?): String? {
        return formatDateFromDateString(
            yyyy_MM_dd__HH_mm_ss,
            OUTPUT_DATE_FORMAT22,
            yyyyMMdd
        )
    }

    fun formatDateFromServerToShortStamp(serverDate: String?): String? {
        return formatDateFromDateString(
            yyyy_MM_dd__HH_mm_ss,
            OUTPUT_DATE_FORMAT_SHORT,
            serverDate
        )
    }

    fun toHHmm_dd_MM_YYYY(input: String?): String? {
        return formatDateFromDateString(
            yyyy_MM_dd__HH_mm_ss,
            HH_mm__dd_MM_yyyy,
            input
        )
    }

    fun formatDateToServerStamp(date: Date?): String? {
        return formatDateTimeFromDate(yyyy_MM_dd__HH_mm_ss, date)
    }

    fun formatDateToServerShortStamp(date: Date?): String? {
        return formatDateTimeFromDate(DATE_FORMAT_3, date)
    }

    fun formatDateToUserStamp(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT3, date)
    }

    fun formatDateToUserShortDateStamp(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT_SHORT, date)
    }
    fun formatDateToUserStamp(string: String?): String? {
        return formatDateFromDateString(
            yyyy_MM_dd__HH_mm_ss,
            OUTPUT_DATE_FORMAT3,
            string
        )
    }

    fun formatDateToUserLongStamp(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT4, date)
    }

    fun getDayOfWeek(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT_DAY_OF_WEEK, date)
    }

    fun getDayOfMonth(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT_DAY_OF_MONTH, date)
    }

    fun getMonthYear(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT_MONTH_YEAR, date)
    }

    fun getHour(date: Date?): String? {
        return formatDateTimeFromDate(OUTPUT_DATE_FORMAT_HOUR_MINUTE, date)
    }

    fun toCalendar(string: String?): Calendar {
        val cal = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat(yyyy_MM_dd__HH_mm_ss, Locale.ENGLISH)
            cal.time = sdf.parse(string!!)!!
        } catch (e: Exception) {
            return cal
        }
        return cal
    }
    fun toCalendarShortStamp(string: String?): Calendar {
        val cal = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat(DATE_FORMAT_3, Locale.ENGLISH)
            cal.time = sdf.parse(string!!)!!
        } catch (e: Exception) {
            return cal
        }
        return cal
    }

    fun formatDateToServerStampShort(ddMMyyyy: String?): String? {
        return formatDateFromDateString(
            OUTPUT_DATE_FORMAT_SHORT,
            DATE_FORMAT_3,
            ddMMyyyy
        )
    }

    fun getMoment(inputDate: String?): String? {
        return return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

            val date: Date = inputFormat.parse(inputDate)!!
            DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } catch (e: ParseException){
            ""
        }
    }
}