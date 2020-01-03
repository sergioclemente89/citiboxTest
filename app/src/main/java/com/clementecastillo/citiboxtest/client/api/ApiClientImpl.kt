package com.clementecastillo.citiboxtest.client.api

import com.clementecastillo.citiboxtest.domain.data.PostDetailsApp
import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ApiClientImpl(
    private val postsApiClient: PostsApiClient
) : ApiClient {

    companion object {
        const val POST_PAGE_ITEM_COUNT = 20
    }

    private fun getPageNumber(itemCount: Int): Int {
        return (itemCount / POST_PAGE_ITEM_COUNT) + 1
    }

    override fun getPosts(currentItemCount: Int, sortField: String, order: String): Single<List<Post>> {
        return postsApiClient.getPosts(getPageNumber(currentItemCount), POST_PAGE_ITEM_COUNT, sortField, order).execute()
    }

    override fun getPost(postId: Int): Single<Post> {
        return postsApiClient.getPost(postId).execute()
    }

    override fun getPostDetails(post: Post): Single<PostDetails> {
        return Single.zip(postsApiClient.getUser(post.userId).execute(), postsApiClient.getPostComments(post.id).execute(), BiFunction { user, comments ->
            PostDetailsApp(post, user, comments)
        })
    }

    private fun <T> Single<out T>.execute(): Single<T> {
        return this.subscribeOn(Schedulers.io()).map { it }
    }
}