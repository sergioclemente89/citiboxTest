package com.clementecastillo.citiboxtest.injection.component

import android.content.res.Resources
import com.clementecastillo.citiboxtest.injection.module.AppModule
import com.clementecastillo.citiboxtestcore.domain.provider.PostProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideResources(): Resources
    fun providePostProvider(): PostProvider
}