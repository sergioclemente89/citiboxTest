package com.clementecastillo.citiboxtestcore.domain.provider

import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.repository.post.PostCacheRepository
import com.clementecastillo.citiboxtestcore.domain.repository.post.PostsApiRepository
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import com.clementecastillo.citiboxtestcore.transaction.unFold
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostProvider @Inject constructor(
    private val postCacheRepository: PostCacheRepository,
    private val postsApiRepository: PostsApiRepository
) {

    fun getPosts(): Single<Transaction<List<Post>>> {
        return postCacheRepository.load().switchIfEmpty(postsApiRepository.getPosts().doOnSuccess {
            it.unFold {
                postCacheRepository.save(it).subscribe()
            }
        })
    }

    fun getMorePosts(currentItemCount: Int): Single<Transaction<List<Post>>> {
        return postsApiRepository.getPosts(currentItemCount).doOnSuccess { newPostTransaction ->
            newPostTransaction.unFold { newPostsList ->
                postCacheRepository.load().subscribe {
                    it.unFold {
                        val cachedPosts = it.toMutableList()
                        cachedPosts.addAll(newPostsList)
                        postCacheRepository.save(cachedPosts).subscribe()
                    }
                }
            }
        }
    }
}