package com.met.tech.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.met.tech.BaseApplication
import com.met.tech.exceptions.UnauthorizedException
import com.met.tech.injection.component.AppComponent
import com.met.tech.util.PrefsUtil
import com.met.tech.util.UiUtil
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    lateinit var navigator: Navigator
    protected lateinit var uiUtil: UiUtil

    @Inject
    lateinit var prefsUtil: PrefsUtil

    private val mCurrentLocale: Locale? = null
    public var dLocale: Locale? = null
    var res: Resources? = null
    var config: Configuration? = null
    var dm: DisplayMetrics? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent =
            (application as BaseApplication).appComponent
        injectActivity(appComponent)
        super.onCreate(savedInstanceState)
        if (prefsUtil.lang.isBlank()) setAppLocale("en") else setAppLocale(prefsUtil.lang)
        bindContentView(layoutId())
        navigator = Navigator(this)
        uiUtil = UiUtil(this)
        observeViewState()
        addObservers()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(this, {
                when (it) {
                    ViewState.Idle -> {
                    }
                    ViewState.Loading -> {
                        uiUtil.showProgress()
                    }
                    is ViewState.Success -> {
                        showToast(it.message)
                    }
                    is ViewState.Error -> {
                        handleException(it.throwable)
                    }
                }
            })
    }

    private fun handleException(throwable: Throwable?) {
        when (throwable) {
            is UnauthorizedException -> showToast(throwable.message)
            else -> showToast(throwable?.message)
        }
    }


    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun injectActivity(appComponent: AppComponent)

    abstract fun addObservers()


    protected fun showToast(
        message: String?,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        message?.let { uiUtil.showToast(it, duration) }
    }

    fun getLayoutBinding() = binding

    protected fun setAppLocale(lang: String) {
        res = resources
        dm = res?.displayMetrics
        config = res?.configuration
        config?.setLocale(Locale(lang))
        res?.updateConfiguration(config, dm)
    }




}