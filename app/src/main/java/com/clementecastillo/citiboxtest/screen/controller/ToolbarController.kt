package com.clementecastillo.citiboxtest.screen.controller

import androidx.annotation.StringRes

interface ToolbarController {
    fun setScreenTitle(@StringRes titleResId: Int)
}