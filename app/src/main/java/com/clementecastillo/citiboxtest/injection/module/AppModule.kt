package com.clementecastillo.citiboxtest.injection.module

import android.content.Context
import android.content.res.Resources
import com.clementecastillo.citiboxtest.CitiboxTestApp
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateClearer
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateLoader
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateSaver
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateStore
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

    @Provides
    @Singleton
    fun resultStateStore(): ResultStateStore = ResultStateStore()

    @Provides
    @Singleton
    fun resultStateSaver(resultStateStore: ResultStateStore): ResultStateSaver = resultStateStore

    @Provides
    @Singleton
    fun resultStateLoader(resultStateStore: ResultStateStore): ResultStateLoader = resultStateStore

    @Provides
    @Singleton
    fun resultStateClearer(resultStateStore: ResultStateStore): ResultStateClearer = resultStateStore

}