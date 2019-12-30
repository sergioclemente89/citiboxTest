package com.clementecastillo.citiboxtest.screen.post.details

import com.clementecastillo.citiboxtest.presenter.PresenterView
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable

interface PostDetailsView : PresenterView {
    fun bindPostDetails(postDetails: PostDetails)
    fun onUserInfoButtonClick(): Observable<User>
}