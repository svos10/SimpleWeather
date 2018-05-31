package com.gmail.segenpro.myweather.data.repositories.core

import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.myweather.domain.Repository
import io.reactivex.Observable
import io.reactivex.Single

abstract class SharedPreferencesRepository<T>(private val rxSharedPreferences: RxSharedPreferences) : Repository<T> {

    abstract fun getPreference(rxSharedPreferences: RxSharedPreferences): Preference<T>

    abstract fun getDefault(): T

    final override fun observe(): Observable<T> = getPreference(rxSharedPreferences).asObservable()

    final override fun observeSingle(): Single<T> = observe().first(getDefault())

    final override fun setAndObserveSingle(value: T): Single<T> = Single.fromCallable {
        getPreference(rxSharedPreferences).set(value)
        value
    }
}
