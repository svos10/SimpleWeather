package com.gmail.segenpro.simpleweather

import org.mockito.Mockito

abstract class BaseTestsClass {

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> eq(value: T): T {
        Mockito.eq<T>(value)
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}
