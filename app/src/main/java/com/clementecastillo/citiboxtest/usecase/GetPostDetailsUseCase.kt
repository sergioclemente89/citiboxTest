package com.clementecastillo.citiboxtest.usecase

import com.clementecastillo.citiboxtest.usecase.common.UseCaseWithParams
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.clementecastillo.citiboxtestcore.domain.provider.PostProvider
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import io.reactivex.Single
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val postProvider: PostProvider) : UseCaseWithParams<Single<Transaction<PostDetails>>, GetPostDetailsUseCase.Params> {

    override fun bind(params: Params): Single<Transaction<PostDetails>> {
        return postProvider.getPostDetails(params.postId)
    }

    class Params(val postId: Int)
}