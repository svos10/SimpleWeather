package com.gmail.segenpro.simpleweather.domain

import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import io.reactivex.Completable
import io.reactivex.Single

interface LocationRepository {

    fun getLocationFromDb(locationId: Long): Single<Result<Location>>

    fun putLocationToDb(searchLocation: SearchLocation): Completable

    fun searchLocationsOnServer(locationName: String): Single<Result<List<SearchLocation>>>
}
