package com.gmail.segenpro.myweather.domain

import io.reactivex.Observable
import io.reactivex.Single

interface Repository {

    fun <T> observe(): Observable<T>

    fun <T> observeSingle(): Single<T>

    fun <T> setAndObserve(value: T): Observable<T>
}