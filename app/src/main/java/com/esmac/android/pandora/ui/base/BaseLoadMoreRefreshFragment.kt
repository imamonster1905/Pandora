package com.esmac.android.pandora.ui.base

import androidx.databinding.ViewDataBinding
import com.esmac.android.pandora.R

abstract class BaseLoadMoreRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> :
    BaseFragment<ViewBinding, ViewModel>() {

    override fun getLayoutId() = R.layout.fragment_loadmore_refresh

}