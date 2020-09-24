package com.esmac.android.pandora.ui.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.esmac.android.pandora.R
import com.esmac.android.pandora.data.local.preferance.PrefManager
import com.esmac.android.pandora.di.module.viewmodel.ViewModelFactory
import com.esmac.android.pandora.util.extention.hideKeyboard
import com.esmac.android.pandora.util.extention.showErrorToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
@SuppressLint("Registered")
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    HasAndroidInjector {

    private lateinit var mViewBinding: B

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var prefManager: PrefManager

    var loadingDialog: AlertDialog? = null

    var mLastLocation = MutableLiveData<Location?>()

    private var isEnableDoubleTabBackPress = false
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        performDataBinding()

        observeException()

        getViewModel().isLoading.observe(this, Observer {
            handleShowLoading(it == true)
        })
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        val v = currentFocus
        if (v != null && (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_MOVE) &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX.plus(v.left).minus(scrcoords[0])
            val y = ev.rawY.plus(v.top).minus(scrcoords[1])
            if (x < v.left || x > v.right || y < v.top || y > v.bottom)
                hideKeyboard()
        }

        return super.dispatchTouchEvent(ev)
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0..view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    open fun handleShowLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else hideLoading()
    }

    fun showLoading() {
        if (loadingDialog == null) {
//            loadingDialog = createLoadingDialog()
        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    private fun observeException() {
        getViewModel().apply {
            networkNotAvailable.observe(this@BaseActivity, Observer {
                it.getContentIfNotHandled()?.let { isOffline ->
                    if (isOffline)
                        showAlert(getString(R.string.message_network_not_available))
                }
            })
            unknownError.observe(this@BaseActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_unknonw_erorr))
                }
            })
            internalServerException.observe(this@BaseActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_internal_server_erorr))
                }
            })
            socketTimeoutException.observe(this@BaseActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    showAlert(getString(R.string.message_socket_timeout_erorr))
                }
            })
            httpException.observe(this@BaseActivity, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    handleHttpException(it)
                }
            })
            expireToken.observe(this@BaseActivity, Observer {
                it.getContentIfNotHandled()?.let { isExpire ->
                    if (isExpire) showExpireTokenAlert()
                }
            })
        }
    }

    open fun handleHttpException(httpException: HttpException) {
        httpException.response()?.errorBody()?.let {
            showAlert(getErrorMessage(it))
        }
    }

    open fun showExpireTokenAlert() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.message_expire_token))
            .setPositiveButton(getString(R.string.txt_login)) { dialog, _ ->
                dialog.dismiss()
                prefManager.clear()
                openActivityOnTokenExpire()
            }
            .setCancelable(false)
            .show()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    open fun getViewDataBinding(): B {
        return mViewBinding
    }

    open fun performDataBinding() {
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewBinding.setVariable(BR.viewModel, getViewModel())
        mViewBinding.lifecycleOwner = this
        mViewBinding.executePendingBindings()
    }

    open fun openActivityOnTokenExpire() {
//        AuthActivity.start(this)
//        finishAffinity()
    }

    open fun showAlert(msg: String?, positiveListener: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(this)
            .setMessage(msg)
            .setPositiveButton(getString(R.string.txt_ok)) { _, _ ->
                positiveListener?.invoke()
            }
            .setCancelable(false)
            .show()
    }

    open fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            )
                return false
        }
        return true
    }

    protected fun getErrorMessage(responseBody: ResponseBody): String? {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("msg")
        } catch (e: Exception) {
            e.message
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {

            if (isEnableDoubleTabBackPress)
                doubleTapToExit()
            else super.onBackPressed()
        }
    }

    private fun doubleTapToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true

        showErrorToast(getString(R.string.message_press_again_to_exit))
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    protected fun enableDoubleTabToExit(enable: Boolean) {
        this.isEnableDoubleTabBackPress = enable
    }
}