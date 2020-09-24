package com.esmac.android.pandora.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.esmac.android.pandora.BR
import com.esmac.android.pandora.R
import com.esmac.android.pandora.di.module.viewmodel.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseDialogFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    DialogFragment(), HasAndroidInjector {

    lateinit var viewBinding: ViewBinding

    abstract fun getViewModel(): ViewModel

    protected lateinit var mActivity: BaseActivity<*, *>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>? {
        return dispatchingAndroidInjector
    }

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = context
            mActivity = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            setVariable(BR.viewModel, getViewModel())
            root.isClickable = true
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }

        observeException()
    }

    private fun observeException() {
        getViewModel().apply {
            networkNotAvailable.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let { isOffline ->
                    if (isOffline)
                        showAlert(getString(R.string.message_network_not_available))
                }
            })
            unknownError.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_unknonw_erorr))
                }
            })
            internalServerException.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_internal_server_erorr))
                }
            })
            socketTimeoutException.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_socket_timeout_erorr))
                }
            })
            httpException.observe(requireActivity(), Observer { event ->
                event.getContentIfNotHandled()?.let {
                    handleHttpException(it)
                }
            })
            expireToken.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let { isExpire ->
                    if (isExpire) showExpireTokenAlert()
                }
            })
        }
    }

    protected open fun handleHttpException(httpException: HttpException) {
        mActivity.handleHttpException(httpException)
    }

    private fun showExpireTokenAlert() {
        mActivity.showExpireTokenAlert()
    }

    open fun openActivityOnTokenExpire() {
        mActivity.openActivityOnTokenExpire()
    }

    open fun getViewDataBinding(): ViewBinding {
        return viewBinding
    }

    protected fun showAlert(msg: String?, positiveListener: (() -> Unit)? = null) {
        (requireActivity() as BaseActivity<*, *>).showAlert(msg, positiveListener)
    }
}