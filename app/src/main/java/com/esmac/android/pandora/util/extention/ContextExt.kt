package com.esmac.android.pandora.util.extention

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.esmac.android.pandora.R
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat


fun Context.showErrorSnackbar(@StringRes strResourceId: Int, rootView: View) {
    val errorSnackbar =
        Snackbar.make(rootView, this.getString(strResourceId), Snackbar.LENGTH_LONG)
    errorSnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
    errorSnackbar.show()
}

fun Context.showErrorSnackbar(msg: CharSequence, rootView: View) {
    val errorSnackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
    errorSnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
    errorSnackbar.show()
}


fun Context.showNormalSnackbar(@StringRes strResourceId: Int, rootView: View) {
    val normalSnackbar =
        Snackbar.make(rootView, this.getString(strResourceId), Snackbar.LENGTH_LONG)
    normalSnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    normalSnackbar.show()
}

fun Context.showNormalSnackbar(msg: CharSequence, rootView: View) {
    val normalSnackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
    normalSnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    normalSnackbar.show()
}

fun Context.showErrorToast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    val view = toast.view
    view.setBackgroundResource(R.drawable.custom_error_background)
    val text = view.findViewById(android.R.id.message) as TextView
    text.setTextColor(Color.WHITE)
    toast.setGravity(Gravity.CENTER, 0, 200)
    toast.show()
}

fun Fragment.showToast(msg: String) {
    val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Fragment.showErrorToastAtTop(msg: String) {
    val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    val view = toast.view
    view.setBackgroundResource(R.drawable.custom_error_background)
    val text = view.findViewById(android.R.id.message) as TextView
    text.setTextColor(Color.WHITE)
    toast.setGravity(Gravity.TOP, 0, 200)
    toast.show()
}

fun Fragment.showErrorToast(msg: String) {
    val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    val view = toast.view
    view.setBackgroundResource(R.drawable.custom_error_background)
    val text = view.findViewById(android.R.id.message) as TextView
    text.setTextColor(Color.WHITE)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Int.toHumanReadableString(): String {
    return NumberFormat.getIntegerInstance().format(this)
}

fun Long.toHumanReadableString(): String {
    return NumberFormat.getIntegerInstance().format(this)
}

inline fun <T> Iterable<T>.firstIndexOrNull(predicate: (T) -> Boolean): Int? {
    return this.mapIndexed { index, item -> Pair(index, item) }
        .firstOrNull() { predicate(it.second) }
        ?.first
}

fun Context.openWebsite(url: String) {
    try {
        val uris = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intent.putExtras(b)
        this.startActivity(intent)
    } catch (ex: Exception) {
        showErrorToast(getString(R.string.error_open_website))
    }
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}