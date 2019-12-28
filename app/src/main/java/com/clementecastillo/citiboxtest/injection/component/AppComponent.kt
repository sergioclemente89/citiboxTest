package com.clementecastillo.citiboxtest.injection.component

import android.content.res.Resources
import com.clementecastillo.citiboxtest.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideResources(): Resources
}