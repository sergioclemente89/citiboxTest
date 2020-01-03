package com.clementecastillo.citiboxtest.usecase

import com.clementecastillo.citiboxtest.client.data.OrderPost
import com.clementecastillo.citiboxtest.client.data.SortPost
import com.clementecastillo.citiboxtest.usecase.common.UseCaseWithParams
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.provider.PostProvider
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import io.reactivex.Single
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postProvider: PostProvider) : UseCaseWithParams<Single<Transaction<List<Post>>>, GetPostsUseCase.Params> {

    override fun bind(params: Params): Single<Transaction<List<Post>>> {

        fun getPostsSingle(): Single<Transaction<List<Post>>> {
            return if (params.currentItemCount == 0) {
                postProvider.getPosts(sortField = params.sortField.fieldName, order = params.order.fieldName)
            } else {
                postProvider.getMorePosts(params.currentItemCount, params.sortField.fieldName, params.order.fieldName)
            }
        }

        return if (params.ignoreCache) {
            postProvider.clearPostsListCache().flatMap { getPostsSingle() }
        } else {
            getPostsSingle()
        }
    }

    class Params(val currentItemCount: Int, val sortField: SortPost, val order: OrderPost, val ignoreCache: Boolean = false)
}