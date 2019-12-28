package com.clementecastillo.citiboxtest.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class Presenter<V : PresenterView> {

    private var disposables: CompositeDisposable = CompositeDisposable()

    fun initWith(presenterView: V) {
        clear()
        disposables = CompositeDisposable()
        init(presenterView)
    }

    fun clear() {
        disposables.dispose()
    }

    protected abstract fun init(view: V)

    fun Disposable.toComposite() {
        addTo(disposables)
    }
}