package com.gmail.segenpro.myweather.data.repositories.core

import com.gmail.segenpro.myweather.domain.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

abstract class MemoryRepository<T> : Repository<T> {

    private val subject by lazy { BehaviorSubject.createDefault<T>(getDefault()) }

    abstract fun getDefault(): T

    final override fun observe(): Observable<T> = subject.hide()

    final override fun observeSingle(): Single<T> = observe().single(getDefault())

    final override fun setAndObserveSingle(value: T): Single<T> = Single.fromCallable {
        subject.onNext(value)
        value
    }
}
