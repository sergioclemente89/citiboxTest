package com.clementecastillo.citiboxtestcore.domain.provider

import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.clementecastillo.citiboxtestcore.domain.repository.post.GetPostApiRepository
import com.clementecastillo.citiboxtestcore.domain.repository.post.GetPostDetailsApiRepository
import com.clementecastillo.citiboxtestcore.domain.repository.post.PostsListApiRepository
import com.clementecastillo.citiboxtestcore.domain.repository.post.PostsListCacheRepository
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import com.clementecastillo.citiboxtestcore.transaction.unFold
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostProvider @Inject constructor(
    private val postsListCacheRepository: PostsListCacheRepository,
    private val postsListApiRepository: PostsListApiRepository,
    private val getPostApiRepository: GetPostApiRepository,
    private val getPostDetailsApiRepository: GetPostDetailsApiRepository
) {

    fun getPosts(): Single<Transaction<List<Post>>> {
        return postsListCacheRepository.load().switchIfEmpty(postsListApiRepository.getPosts().doOnSuccess {
            it.unFold {
                postsListCacheRepository.save(it).subscribe()
            }
        })
    }

    fun getMorePosts(currentItemCount: Int): Single<Transaction<List<Post>>> {
        return postsListApiRepository.getPosts(currentItemCount).doOnSuccess { newPostTransaction ->
            newPostTransaction.unFold { newPostsList ->
                postsListCacheRepository.load().subscribe {
                    it.unFold {
                        val cachedPosts = it.toMutableList()
                        cachedPosts.addAll(newPostsList)
                        postsListCacheRepository.save(cachedPosts).subscribe()
                    }
                }
            }
        }
    }

    fun getPostDetails(postId: Int): Single<Transaction<PostDetails>> {
        return postsListCacheRepository.load().map<Transaction<Post>> {
            (it as? Transaction.Success)?.let {
                it.data.find { it.id == postId }?.let {
                    Transaction.Success(it)
                }
            }
        }.switchIfEmpty(getPostApiRepository.getPost(postId)).flatMap {
            when (it) {
                is Transaction.Success -> getPostDetailsApiRepository.getPostDetails(it.data)
                is Transaction.Fail -> Single.just(Transaction.Fail(it.throwable))
            }
        }
    }
}