package com.clementecastillo.citiboxtest.client.api

import com.clementecastillo.citiboxtest.domain.data.CommentApp
import com.clementecastillo.citiboxtest.domain.data.PostApp
import com.clementecastillo.citiboxtest.domain.data.UserApp
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiClient {

    companion object {
        const val HOST = "https://jsonplaceholder.typicode.com"
    }

    @GET("/posts")
    fun getPosts(@Query("_page") page: Int, @Query("_limit") pageCount: Int): Single<List<PostApp>>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserApp>

    @GET("/posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Single<List<CommentApp>>
}