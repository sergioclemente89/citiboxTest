package com.clementecastillo.citiboxtestcore.domain.repository

import com.clementecastillo.citiboxtestcore.transaction.CompletableTransaction
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

abstract class CacheRepository<T>(private val transactionRequest: TransactionRequest, private var validityTime: Long = VALIDITY_LONG_TIME) {

    companion object {
        const val VALIDITY_LONG_TIME = 1200000L
        const val VALIDITY_SHORT_TIME = 150000L
    }

    private var data: T? = null
    private var timestamp: Long = 0

    open fun load(): Maybe<Transaction<T>> {
        return transactionRequest.wrap(Maybe.create { subscriber ->
            if (data != null && isValidTime()) {
                subscriber.onSuccess(data!!)
            } else {
                data = null
                subscriber.onComplete()
            }
        })
    }

    fun save(value: T): Single<CompletableTransaction> {
        return transactionRequest.wrap(Completable.create { subscriber ->
            this.data = value
            updateTimestamp()
            subscriber.onComplete()
        })
    }

    fun clear(): Single<CompletableTransaction> {
        return transactionRequest.wrap(Completable.create { subscriber ->
            this.data = null
            subscriber.onComplete()
        })
    }

    private fun updateTimestamp() {
        timestamp = System.currentTimeMillis()
    }

    private fun isValidTime(): Boolean {
        return System.currentTimeMillis() - timestamp < validityTime
    }
}