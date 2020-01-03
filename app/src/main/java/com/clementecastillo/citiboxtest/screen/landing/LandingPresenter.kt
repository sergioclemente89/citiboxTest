package com.clementecastillo.citiboxtest.screen.landing

import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LandingPresenter @Inject constructor(
    private val loadingController: LoadingController
) : Presenter<LandingView>() {
    override fun init(view: LandingView) {
        loadingController.showLoading()

        Completable.timer(4, TimeUnit.SECONDS).subscribe {
            loadingController.hideLoading()
        }.toComposite()
    }
}