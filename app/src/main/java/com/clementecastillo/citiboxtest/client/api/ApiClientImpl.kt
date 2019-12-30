package com.clementecastillo.citiboxtest.client.api

import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.domain.data.Post
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ApiClientImpl(
    private val postsApiClient: PostsApiClient
) : ApiClient {

    companion object {
        const val POST_PAGE_ITEM_COUNT = 20
    }

    private fun getPageNumber(itemCount: Int): Int {
        return itemCount / POST_PAGE_ITEM_COUNT
    }

    override fun getPosts(currentItemCount: Int): Single<List<Post>> {
        return postsApiClient.getPosts(getPageNumber(currentItemCount), POST_PAGE_ITEM_COUNT).execute()
    }

    private fun <T> Single<out T>.execute(): Single<T> {
        return this.subscribeOn(Schedulers.io()).map { it }
    }
}