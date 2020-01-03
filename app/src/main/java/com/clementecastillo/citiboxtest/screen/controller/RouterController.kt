package com.clementecastillo.citiboxtest.screen.controller

import com.clementecastillo.citiboxtest.screen.base.BaseDialogFragment
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable

interface RouterController {
    fun routeToLanding()
    fun routeToPostList()
    fun routeToPostDetails(postId: Int)
    fun showUserInfoDialog(user: User)
    fun sharePost(post: Post)
    fun sendEmail(email: String)
    fun openCaller(phone: String)
    fun openNavigation(latitude: String, longitude: String)
    fun showErrorDialogObservable(messageRes: Int? = null): Observable<BaseDialogFragment.DialogStateEvent>
    fun goBack()
}