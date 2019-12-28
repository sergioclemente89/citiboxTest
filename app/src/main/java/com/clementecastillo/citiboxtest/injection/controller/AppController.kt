package com.clementecastillo.citiboxtest.injection.controller

import com.clementecastillo.citiboxtest.injection.component.AppComponent

interface AppController {
    val appComponent: AppComponent
}