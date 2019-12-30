package com.clementecastillo.citiboxtest.injection.module

import android.content.Context
import android.content.res.Resources
import com.clementecastillo.citiboxtest.CitiboxTestApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(
    includes = [ClientModule::class]
)
class AppModule(private val app: CitiboxTestApp) {

    @Provides
    @Singleton
    fun resources(): Resources = app.resources

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext
}