package com.clementecastillo.citiboxtestcore.domain.repository.post

import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsApiRepository @Inject constructor(
    private val transactionRequest: TransactionRequest,
    private val apiClient: ApiClient
) {
    fun getPosts(currentItemCount: Int = 0): Single<Transaction<List<Post>>> {
        return transactionRequest.wrap(apiClient.getPosts(currentItemCount))
    }
}