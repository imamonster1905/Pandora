package com.esmac.android.pandora.util.extention

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esmac.android.pandora.BuildConfig
import com.esmac.android.pandora.util.commom.Constants

const val placeHolder = Constants.placeHolder

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.customRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
    if (listener != null) setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?) {
    isRefreshing = refreshing == true
}

@BindingAdapter("onScrollListener")
fun RecyclerView.customScrollListener(listener: RecyclerView.OnScrollListener?) {
    if (listener != null) addOnScrollListener(listener)
}

@BindingAdapter("visibleOrGone")
fun setVisibleOrGone(view: View, show: Boolean?) {
    view.visibility = if (show != null && show)
        View.VISIBLE
    else View.GONE
}

@BindingAdapter("visible")
fun setVisible(view: View, show: Boolean) {
    view.visibility = if (show)
        View.VISIBLE
    else View.INVISIBLE
}

@BindingAdapter("enableDebugUI")
fun setEnableDebugUI(view: View, show: Boolean) {
    view.visibility = if (BuildConfig.DEBUG && show)
        View.VISIBLE
    else View.GONE
}

@BindingAdapter("clickSafe")
fun View.setClickSafe(listener: View.OnClickListener?) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            listener?.onClick(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

@BindingAdapter("onSingleClick")
fun View.setSingleClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke()
        }
    })
}

@BindingAdapter("onFiveClick")
fun View.setFiveClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var count = 0

        override fun onClick(p0: View?) {
            if (count < 5) {
                count += 1
                return
            } else {
                execution.invoke()
                count = 0
            }
        }
    })
}
