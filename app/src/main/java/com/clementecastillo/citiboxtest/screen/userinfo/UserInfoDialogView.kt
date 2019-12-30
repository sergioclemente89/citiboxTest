package com.clementecastillo.citiboxtest.screen.userinfo

import com.clementecastillo.citiboxtest.presenter.PresenterView
import com.clementecastillo.citiboxtestcore.domain.data.Address
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable

interface UserInfoDialogView : PresenterView {
    fun bindUser(user: User)
    fun onAddressClick(): Observable<Address>
    fun onPhoneClick(): Observable<String>
    fun onEmailClick(): Observable<String>
}