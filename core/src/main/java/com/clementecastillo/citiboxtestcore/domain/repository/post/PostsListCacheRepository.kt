package com.clementecastillo.citiboxtestcore.domain.repository.post

import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.repository.CacheRepository
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsListCacheRepository @Inject constructor(transactionRequest: TransactionRequest) : CacheRepository<List<Post>>(transactionRequest)