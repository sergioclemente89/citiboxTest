package com.clementecastillo.citiboxtest

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.clementecastillo.citiboxtest.injection.component.AppComponent
import com.clementecastillo.citiboxtest.injection.component.DaggerAppComponent
import com.clementecastillo.citiboxtest.injection.controller.AppController
import com.clementecastillo.citiboxtest.injection.module.AppModule

class CitiboxTestApp : Application(), AppController {
    companion object {
        lateinit var appController: AppController
    }

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appController = this
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

}