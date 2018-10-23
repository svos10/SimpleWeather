package com.gmail.segenpro.myweather.data.repositories.location

import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.myweather.data.repositories.core.SharedPreferencesRepository
import com.gmail.segenpro.myweather.domain.CURRENT_LOCATION_ID_NOT_SET
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_CURRENT_LOCATION_ID = "key_current_location_id"

@Singleton
class CurrentLocationIdDataRepository @Inject constructor(rxSharedPreferences: RxSharedPreferences) :
        SharedPreferencesRepository<Long>(rxSharedPreferences) {

    override fun getPreference(rxSharedPreferences: RxSharedPreferences): Preference<Long> =
            rxSharedPreferences.getLong(KEY_CURRENT_LOCATION_ID)

    override fun getDefault(): Long = CURRENT_LOCATION_ID_NOT_SET
}
