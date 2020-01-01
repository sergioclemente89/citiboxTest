package com.clementecastillo.citiboxtest.screen.controller

import androidx.annotation.StringRes
import io.reactivex.Observable

interface ToolbarController {
    fun setScreenTitle(@StringRes titleResId: Int)
    fun showBackButton()
    fun setToolbarMenu(menu: Int): Observable<Int>
    fun clearToolbarMenu()
}