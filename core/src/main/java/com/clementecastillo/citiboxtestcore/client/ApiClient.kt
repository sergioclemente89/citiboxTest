package com.clementecastillo.citiboxtestcore.client

import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import io.reactivex.Single

interface ApiClient {
    fun getPosts(currentItemCount: Int): Single<List<Post>>
    fun getPost(postId: Int): Single<Post>
    fun getPostDetails(post: Post): Single<PostDetails>
}