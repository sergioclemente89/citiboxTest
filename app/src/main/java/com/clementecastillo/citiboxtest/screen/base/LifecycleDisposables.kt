package com.clementecastillo.citiboxtest.screen.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface LifecycleDisposables {

    var disposables: CompositeDisposable?

    fun clearComposite() {
        disposables?.clear()
    }

    fun Disposable.toComposite() {
        disposables?.add(this)
    }
}