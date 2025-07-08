package net.mioyi.kortex.di

import kotlin.reflect.KProperty

interface Injection<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

inline fun <reified T> inject(qualifier: String = "") = object : Injection<T> {
    private val bean by lazy {
        DependencyContainer.get<T>(qualifier)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return bean
    }
}