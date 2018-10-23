package com.gmail.segenpro.myweather.domain

import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import io.reactivex.Completable
import io.reactivex.Single

interface LocationRepository {

    fun getLocationFromDb(locationId: Long): Single<Result<Location>>

    fun putLocationToDb(searchLocation: SearchLocation): Completable

    fun searchLocationsAtServer(locationName: String): Single<Result<List<SearchLocation>>>
}
