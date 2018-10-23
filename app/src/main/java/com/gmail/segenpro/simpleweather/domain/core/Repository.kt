package com.gmail.segenpro.simpleweather.domain.core

import io.reactivex.Observable
import io.reactivex.Single

interface Repository<T> {

    fun observe(): Observable<T>

    fun observeSingle(): Single<T>

    fun setAndObserveSingle(value: T): Single<T>
}