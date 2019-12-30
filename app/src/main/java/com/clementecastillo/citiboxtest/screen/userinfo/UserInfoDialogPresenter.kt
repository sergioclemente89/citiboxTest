package com.clementecastillo.citiboxtest.screen.userinfo

import com.clementecastillo.citiboxtest.extension.throttleDefault
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateLoader
import javax.inject.Inject

class UserInfoDialogPresenter @Inject constructor(
    private val resultStateLoader: ResultStateLoader,
    private val routerController: RouterController
) : Presenter<UserInfoDialogView>() {

    override fun init(view: UserInfoDialogView) {
        view.run {
            resultStateLoader.load<UserInfoResultState>(UserInfoDialogView::class).let {
                if (it == null) {
                    // TODO: Show routing error
                } else {
                    bindUser(it.user)
                }
            }

            onAddressClick().throttleDefault().subscribe {
                routerController.openNavigation(it.geoLocation.lat, it.geoLocation.lng)
            }.toComposite()

            onPhoneClick().throttleDefault().subscribe {
                routerController.openCaller(it)
            }.toComposite()

            onEmailClick().throttleDefault().subscribe {
                routerController.sendEmail(it)
            }.toComposite()
        }
    }
}