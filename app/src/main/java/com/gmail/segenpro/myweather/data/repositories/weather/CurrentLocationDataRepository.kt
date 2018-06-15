package com.gmail.segenpro.myweather.data.repositories.weather

import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.myweather.data.repositories.core.SharedPreferencesRepository
import com.gmail.segenpro.myweather.domain.CURRENT_LOCATION_NOT_SET
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_LOCATION = "key_location"

@Singleton
class LocationDataRepository @Inject constructor(rxSharedPreferences: RxSharedPreferences) :
        SharedPreferencesRepository<Long>(rxSharedPreferences) {
    override fun getPreference(rxSharedPreferences: RxSharedPreferences): Preference<Long> =
            rxSharedPreferences.getLong(KEY_LOCATION)

    override fun getDefault(): Long = CURRENT_LOCATION_NOT_SET
}
