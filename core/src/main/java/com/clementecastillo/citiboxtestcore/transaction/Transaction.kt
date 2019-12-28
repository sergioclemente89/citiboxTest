package com.clementecastillo.citiboxtestcore.transaction

import io.reactivex.Single

sealed class Transaction<T> {
    class Success<T>(val data: T) : Transaction<T>()
    class Fail<T>(val throwable: Throwable? = null) : Transaction<T>()
}

class CompletableTransaction(val success: Boolean, val throwable: Throwable? = null)

fun Transaction<*>.isSuccess(): Boolean {
    return this is Transaction.Success<*>
}

fun CompletableTransaction.isSuccess() = this.success

fun CompletableTransaction.unFold(onSuccess: (() -> Unit)) {
    unFold(onSuccess, null)
}

fun CompletableTransaction.unFold(onSuccess: (() -> Unit)?, onError: ((Throwable?) -> Unit)?) {
    if (isSuccess()) {
        onSuccess?.invoke()
    } else {
        onError?.invoke(throwable)
    }
}

fun <T> Transaction<T>.unFold(onSuccess: ((T) -> Unit)) {
    unFold(onSuccess, null)
}

fun <T> Transaction<T>.unFold(onSuccess: ((T) -> Unit)?, onError: ((Throwable?) -> Unit)?) {
    when (this) {
        is Transaction.Success<T> -> onSuccess?.invoke(data)
        is Transaction.Fail<T> -> onError?.invoke(throwable)
    }
}

fun <T> CompletableTransaction.mapSuccess(function: () -> T): Transaction<T> {
    return if (isSuccess()) {
        Transaction.Success(function())
    } else {
        Transaction.Fail(null)
    }
}

fun <T, R> Transaction<T>.mapSuccess(function: (T) -> R): Transaction<R> {
    return when (this) {
        is Transaction.Success<T> -> Transaction.Success(function(data))
        is Transaction.Fail<T> -> this.mapError()
    }
}

fun <T, R> Transaction.Fail<T>.mapError(): Transaction<R> {
    return Transaction.Fail(throwable)
}

fun <T> Single<Transaction<T>>.toCompletableTransaction(): Single<CompletableTransaction> {
    return this.map {
        when (it) {
            is Transaction.Success -> CompletableTransaction(true)
            is Transaction.Fail -> CompletableTransaction(false, it.throwable)
        }

    }
}