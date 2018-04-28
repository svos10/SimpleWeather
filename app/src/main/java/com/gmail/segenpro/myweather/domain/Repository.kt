package com.gmail.segenpro.myweather.domain

import io.reactivex.Observable
import io.reactivex.Single

interface Repository<T> {

    fun observe(): Observable<T>

    fun observeSingle(): Single<T>

    fun setAndObserveSingle(value: T): Single<T>
}