package com.clementecastillo.citiboxtest.screen.splash

import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val routerController: RouterController
) : Presenter<SplashView>() {

    override fun init(view: SplashView) {
        view.onAnimationFinish().subscribe {
            routerController.routeToLanding()
        }.toComposite()
    }
}