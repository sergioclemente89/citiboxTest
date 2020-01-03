package com.clementecastillo.citiboxtest.screen.splash

import com.clementecastillo.citiboxtest.presenter.PresenterView
import io.reactivex.Observable

interface SplashView : PresenterView {
    fun onAnimationFinish(): Observable<Unit>
}