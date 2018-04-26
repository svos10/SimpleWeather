package com.gmail.segenpro.myweather.data

import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.myweather.domain.Repository
import io.reactivex.Observable
import io.reactivex.Single


abstract class SharedPreferencesRepository(private val rxSharedPreferences: RxSharedPreferences) : Repository {

    abstract fun <T> getPreference(rxSharedPreferences: RxSharedPreferences): Preference<T>

    abstract fun <T> getDefault(): T

    override fun <T> observe(): Observable<T> = getPreference<T>(rxSharedPreferences).asObservable()

    override fun <T> observeSingle(): Single<T> = observe<T>().single(getDefault())

    override fun <T> setAndObserve(value: T): Observable<T> = Observable.fromCallable {
        getPreference<T>(rxSharedPreferences).set(value)
        value
    }
}