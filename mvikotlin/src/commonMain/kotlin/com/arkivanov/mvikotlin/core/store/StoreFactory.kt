package com.arkivanov.mvikotlin.core.store

import kotlin.js.JsName

/**
 * Creates instances of [Store]s using the provided components.
 * You can create different [Store] wrappers and combine them depending on circumstances.
 */
interface StoreFactory {

    val isAutoInitByDefault: Boolean

    /**
     * Creates an implementation of [Store].
     * Must be called only on the main thread if [isAutoInit] argument is true (default).
     * Can be called on any thread if the [isAutoInit] is false.
     *
     * @param name a name of the [Store] being created, used for logging, time traveling, etc.
     * @param isAutoInit if `true` then the [Store] will be automatically initialized after creation,
     * otherwise call [Store.init] manually
     */
    @JsName("create")
    fun <Intent : Any, Action : Any, Result : Any, State : Any, Label : Any> create(
        name: String? = null,
        isAutoInit: Boolean = isAutoInitByDefault,
        initialState: State,
        bootstrapper: Bootstrapper<Action>? = null,
        executorFactory: () -> Executor<Intent, Action, State, Result, Label>,
        @Suppress("UNCHECKED_CAST")
        reducer: Reducer<State, Result> = bypassReducer as Reducer<State, Any>
    ): Store<Intent, State, Label>

    private companion object {
        private val bypassReducer: Reducer<Any, Any> = Reducer { this }
    }
}
