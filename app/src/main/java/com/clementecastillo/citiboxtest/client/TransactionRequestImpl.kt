package com.clementecastillo.citiboxtest.client

import com.clementecastillo.citiboxtest.BuildConfig
import com.clementecastillo.citiboxtestcore.domain.repository.EmptyCacheException
import com.clementecastillo.citiboxtestcore.transaction.CompletableTransaction
import com.clementecastillo.citiboxtestcore.transaction.Transaction
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class TransactionRequestImpl : TransactionRequest {

    override fun <T> wrap(single: Single<T>): Single<Transaction<T>> {
        return single.map<Transaction<T>> {
            Transaction.Success(it)
        }.doOnError {
            if (BuildConfig.DEBUG && it !is EmptyCacheException) {
                it.printStackTrace()
            }
        }.onErrorResumeNext { throwable: Throwable ->
            Single.just(Transaction.Fail(throwable))
        }
    }

    override fun <T> wrap(observable: Observable<T>): Observable<Transaction<T>> {
        return observable.map<Transaction<T>> {
            Transaction.Success(it)
        }.doOnError {
            if (BuildConfig.DEBUG && it !is EmptyCacheException) {
                it.printStackTrace()
            }
        }.onErrorResumeNext { throwable: Throwable ->
            Observable.just(Transaction.Fail(throwable))
        }
    }

    override fun wrap(completable: Completable): Single<CompletableTransaction> {
        return completable.toSingleDefault(CompletableTransaction(true)).onErrorReturn { CompletableTransaction(false) }
    }

}
