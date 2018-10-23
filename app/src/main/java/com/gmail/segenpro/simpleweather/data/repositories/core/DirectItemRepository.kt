package com.gmail.segenpro.simpleweather.data.repositories.core

import com.gmail.segenpro.simpleweather.domain.core.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

abstract class DirectItemRepository<T> : Repository<T> {

    private val subject by lazy { PublishSubject.create<T>() }

    final override fun observe(): Observable<T> = subject.hide()

    final override fun observeSingle(): Single<T> = observe().firstOrError()

    final override fun setAndObserveSingle(value: T): Single<T> = Single.fromCallable {
        subject.onNext(value)
        value
    }
}
