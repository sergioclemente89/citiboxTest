package com.clementecastillo.citiboxtestcore.transaction

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface TransactionRequest {
    fun <T> wrap(single: Single<T>): Single<Transaction<T>>
    fun <T> wrap(observable: Observable<T>): Observable<Transaction<T>>
    fun wrap(completable: Completable): Single<CompletableTransaction>
}