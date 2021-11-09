package com.met.tech

import android.app.Application
import com.met.tech.injection.component.AppComponent
import com.met.tech.injection.component.DaggerAppComponent

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}
