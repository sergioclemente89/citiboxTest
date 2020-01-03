package com.clementecastillo.citiboxtest.screen.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.clementecastillo.citiboxtest.extension.takeWhen
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class LifecycleActivity : AppCompatActivity(), LifecycleDisposables {

    enum class LifecycleState {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
    }

    private val lifecycleEmitter: BehaviorSubject<LifecycleState> = BehaviorSubject.create()
    override var disposables: CompositeDisposable? = null

    fun lifecycleEvents(): Observable<LifecycleState> {
        return lifecycleEmitter
    }

    fun onAlive(): Observable<LifecycleState> {
        return lifecycleEmitter.takeWhen { it == LifecycleState.STARTED || it == LifecycleState.RESUMED }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
        lifecycleEmitter.onNext(LifecycleState.CREATED)
    }

    override fun onStart() {
        super.onStart()
        lifecycleEmitter.onNext(LifecycleState.STARTED)
    }

    override fun onResume() {
        super.onResume()
        lifecycleEmitter.onNext(LifecycleState.RESUMED)
    }

    override fun onPause() {
        lifecycleEmitter.onNext(LifecycleState.PAUSED)
        super.onPause()
    }

    override fun onStop() {
        lifecycleEmitter.onNext(LifecycleState.STOPPED)
        super.onStop()
    }

    override fun onDestroy() {
        clearComposite()
        lifecycleEmitter.onNext(LifecycleState.DESTROYED)
        super.onDestroy()
    }
}