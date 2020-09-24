package com.esmac.android.pandora.ui.base


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import com.esmac.android.pandora.BR
import com.esmac.android.pandora.R
import com.esmac.android.pandora.di.module.viewmodel.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import retrofit2.HttpException
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    HasAndroidInjector {

    lateinit var mViewDataBinding: B

    private lateinit var mViewModel: VM

    abstract fun getViewModel(): VM

    protected lateinit var mActivity: BaseActivity<*, *>

    var loadingDialog: ProgressDialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>? {
        return dispatchingAndroidInjector
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = context
            mActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
//        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(BR.viewModel, mViewModel)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mViewDataBinding.executePendingBindings()
        getViewModel().isLoading.observe(viewLifecycleOwner, Observer {
            handleShowLoading(it)
        })
//        getViewModel().httpExceptionMsg.observe(viewLifecycleOwner, Observer { event ->
//            event.getContentIfNotHandled()?.let { (activity as BaseActivity<*, *>).showAlert(it) }
//        })
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

    open fun getViewDataBinding(): B {
        return mViewDataBinding
    }

    open fun handleShowLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else hideLoading()
    }

    fun showLoading() {
        if (loadingDialog == null) {

        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    protected fun showAlert(msg: String?, positiveListener: (() -> Unit)? = null) {
        (requireActivity() as BaseActivity<*, *>).showAlert(msg, positiveListener)
    }

    //support navigate with animation left-right
    fun navOptionsHorizontal(): NavOptions {
        val builder = NavOptions.Builder()
            .setEnterAnim(R.anim.left_in)
            .setExitAnim(R.anim.right_out)
            .setPopEnterAnim(R.anim.left_in)
            .setPopExitAnim(R.anim.right_out)
        return builder.build()
    }

    //support navigate with animation left-right
    fun navOptionsFade(): NavOptions {
        val builder = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
        return builder.build()
    }

    //support navigate with animation up-down
    fun navOptionsPopup(): NavOptions {
        val builder = NavOptions.Builder()
            .setEnterAnim(R.anim.move_up)
            .setExitAnim(R.anim.move_down)
            .setPopEnterAnim(R.anim.move_up)
            .setPopExitAnim(R.anim.move_down)
        return builder.build()
    }
}
