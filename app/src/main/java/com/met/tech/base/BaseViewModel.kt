package com.met.tech.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.met.tech.exceptions.UnauthorizedException
import com.met.tech.util.toLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

open class BaseViewModel() : ViewModel() {


    protected val _viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val viewState = _viewState.toLiveData()


    fun launch(
        code: suspend CoroutineScope.() -> Unit
    ) {
        (viewModelScope + exceptionHandler).launch {
            code.invoke(this)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleFailure(throwable = exception)
    }

    private fun handleFailure(throwable: Throwable?) {
        if (throwable is UnauthorizedException) {
            _viewState.postValue(ViewState.Error(throwable))
        }
        _viewState.postValue(ViewState.Error(throwable))
    }

}