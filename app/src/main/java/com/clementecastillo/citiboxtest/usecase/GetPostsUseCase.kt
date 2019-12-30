package com.clementecastillo.citiboxtest.usecase

import com.clementecastillo.citiboxtest.usecase.common.UseCaseWithParams
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.provider.PostProvider
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import io.reactivex.Single
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postProvider: PostProvider) : UseCaseWithParams<Single<Transaction<List<Post>>>, GetPostsUseCase.Params> {

    override fun bind(params: Params): Single<Transaction<List<Post>>> {
        return if (params.currentItemCount == 0) {
            postProvider.getPosts()
        } else {
            postProvider.getMorePosts(params.currentItemCount)
        }
    }

    class Params(val currentItemCount: Int)
}