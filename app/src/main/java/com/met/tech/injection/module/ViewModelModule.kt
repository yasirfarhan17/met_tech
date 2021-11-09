package com.met.tech.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.met.tech.injection.scope.ViewModelScope
import com.met.tech.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelScope(SplashViewModel::class)
    abstract fun bindMainViewModel(viewModel: SplashViewModel): ViewModel



}

