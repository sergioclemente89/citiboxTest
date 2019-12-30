package com.clementecastillo.citiboxtest.usecase

import com.clementecastillo.citiboxtest.usecase.common.UseCase
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.provider.PostProvider
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import io.reactivex.Single
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postProvider: PostProvider) : UseCase<Single<Transaction<List<Post>>>> {

    override fun bind(): Single<Transaction<List<Post>>> {
        return postProvider.getPosts()
    }
}