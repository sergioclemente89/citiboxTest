package com.clementecastillo.citiboxtest.injection.module

import android.content.res.Resources
import com.clementecastillo.citiboxtest.CitiboxTestApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: CitiboxTestApp) {

    @Provides
    @Singleton
    fun resources(): Resources = app.resources
}