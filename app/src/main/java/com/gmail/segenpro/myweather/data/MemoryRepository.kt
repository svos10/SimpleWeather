package com.gmail.segenpro.myweather.data

import com.gmail.segenpro.myweather.domain.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

abstract class MemoryRepository<T> : Repository<T> {

    private val subject by lazy { BehaviorSubject.createDefault<T>(getDefault()) }

    abstract fun getDefault(): T

    override fun observe(): Observable<T> = subject.hide()

    override fun observeSingle(): Single<T> = observe().single(getDefault())

    override fun setAndObserveSingle(value: T): Single<T> = Single.fromCallable {
        subject.onNext(value)
        value
    }
}
