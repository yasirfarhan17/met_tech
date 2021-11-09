package com.met.tech.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.met.tech.BaseApplication
import com.met.tech.exceptions.UnauthorizedException
import com.met.tech.injection.component.AppComponent
import com.met.tech.util.PrefsUtil
import com.met.tech.util.UiUtil
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected var bindToActivityViewModel = false
    protected lateinit var uiUtil: UiUtil

    @Inject
    lateinit var prefsUtil: PrefsUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent =
            (requireContext().applicationContext as BaseApplication).appComponent
        injectFragment(appComponent)
        super.onCreate(savedInstanceState)
        viewModel = if (bindToActivityViewModel.not())
            ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
        else
            activity?.run {
                ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
            } ?: throw Exception("Invalid Activity")
        uiUtil = UiUtil(requireContext())
        observeViewState()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(this, {
                when (it) {
                    ViewState.Idle -> {
                    }
                    ViewState.Loading -> {
                        showProgress()
                    }
                    is ViewState.Success -> {
                        hideProgress()
                        showMessage(it.message)
                    }
                    is ViewState.Error -> {
                        hideProgress()
                        handleException(it.throwable)
                    }
                }
            })
    }

    private fun handleException(throwable: Throwable?) {
        when (throwable) {
            is UnauthorizedException -> showToast(throwable.message)
            else -> showMessage(throwable?.message)
        }
    }


    protected fun showProgress() {
        uiUtil.showProgress()
    }

    protected fun hideProgress() {
        uiUtil.hideProgress()
    }

    protected fun showMessage(message: String?) {
        message?.let { uiUtil.showMessage(it) }
    }

    protected fun showToast(
        message: String?,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        message?.let {
            Toast.makeText(requireContext(), it, duration)
                .show()
        }
    }

    protected fun getNavigator() = (activity as BaseActivity<*, *>).navigator

    abstract fun getViewModelClass(): Class<VM>

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun injectFragment(appComponent: AppComponent)

    abstract fun addObservers()
}
