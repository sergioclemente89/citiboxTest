package com.clementecastillo.citiboxtest.screen.post.list

import com.clementecastillo.citiboxtest.presenter.PresenterView
import com.clementecastillo.citiboxtestcore.domain.data.Post
import io.reactivex.Observable

interface PostListView : PresenterView {
    fun setPosts(postsList: List<Post>)
    fun addPosts(postsList: List<Post>)
    fun onRequestNextPage(): Observable<Int>
    fun onPostClick(): Observable<Int>
    fun onSharePostClick(): Observable<Post>
    fun onRefreshList(): Observable<Unit>
}