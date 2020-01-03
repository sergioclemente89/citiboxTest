package com.clementecastillo.citiboxtest.injection.module

import com.clementecastillo.citiboxtest.injection.ScreenScope
import com.clementecastillo.citiboxtest.screen.base.BaseActivity
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import dagger.Module
import dagger.Provides

@Module
class ScreenModule(private val activity: BaseActivity) {

    @Provides
    @ScreenScope
    fun routerController(): RouterController = activity

    @Provides
    @ScreenScope
    fun loadingController(): LoadingController = activity

    @Provides
    @ScreenScope
    fun toolbarController(): ToolbarController = activity
}