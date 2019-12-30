package com.clementecastillo.citiboxtest.screen.resultstate

import com.clementecastillo.citiboxtest.presenter.PresenterView
import kotlin.reflect.KClass

interface ResultStateSaver {

    fun <T : ResultState> save(key: KClass<out PresenterView>, state: T)
}

interface ResultStateLoader {

    fun <T : ResultState> load(key: KClass<out PresenterView>): T?
}

interface ResultStateClearer {

    fun clear(key: KClass<out PresenterView>)

    fun clearAll()
}

class ResultStateStore : ResultStateSaver, ResultStateLoader, ResultStateClearer {

    private val map: MutableMap<KClass<out PresenterView>, ResultState> = mutableMapOf()

    override fun <T : ResultState> save(key: KClass<out PresenterView>, state: T) {
        map[key] = state
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ResultState> load(key: KClass<out PresenterView>): T? {
        return map[key] as T?
    }

    override fun clear(key: KClass<out PresenterView>) {
        map.remove(key)
    }

    override fun clearAll() {
        map.clear()
    }
}