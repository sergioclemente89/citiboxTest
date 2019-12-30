package com.clementecastillo.citiboxtestcore.client

import com.clementecastillo.citiboxtestcore.domain.data.Post
import io.reactivex.Single

interface ApiClient {
    fun getPosts(currentItemCount: Int): Single<List<Post>>
}