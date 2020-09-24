package com.esmac.android.pandora.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseRefreshViewModel : BaseViewModel() {

    open val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        if (isLoading.value == true
            || isRefreshing.value == true
        ) return@OnRefreshListener
        isRefreshing.value = true
        refreshData()
    }

    val isRefreshing = MutableLiveData<Boolean>(true)

    val isLoadMore = MutableLiveData<Boolean>().apply { value = false }

    /**
     * handle throwable when load fail
     */
    override suspend fun onLoadFail(throwable: Throwable) {
        super.onLoadFail(throwable)
        withContext(Dispatchers.Main) {
            super.onLoadFail(throwable)
            // reset load
            isRefreshing.value = false
            isLoadMore.value = false
        }
    }

    open fun refreshData() {
        hideLoading()
        isRefreshing.value = false
    }
}