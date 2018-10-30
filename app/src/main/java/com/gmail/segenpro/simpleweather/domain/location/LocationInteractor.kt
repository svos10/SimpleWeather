package com.gmail.segenpro.simpleweather.domain.location

import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.CURRENT_LOCATION_ID_NOT_SET
import com.gmail.segenpro.simpleweather.domain.LocationRepository
import com.gmail.segenpro.simpleweather.domain.core.Repository
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LocationInteractor @Inject constructor(private val locationRepository: LocationRepository,
                                             @Named("currentLocationId") private val currentLocationIdRepository: Repository<Long>) {

    fun getCurrentLocation(): Observable<Result<Location>> = currentLocationIdRepository.observe()
            .flatMapSingle {
                if (it == CURRENT_LOCATION_ID_NOT_SET) {
                    Single.just(WeatherException(ErrorType.LOCATION_NOT_SELECTED).asErrorResult())
                } else {
                    locationRepository.getLocationFromDb(it)
                }
            }

    fun setCurrentLocation(searchLocation: SearchLocation): Completable =
            getCurrentLocation()
                    .flatMapCompletable {
                        if (it is Result.Error || (it as Result.Success).data.id != searchLocation.id) {
                            locationRepository.putLocationToDb(searchLocation)
                                    .andThen(currentLocationIdRepository.setAndObserveSingle(searchLocation.id).ignoreElement())
                        } else Completable.complete()
                    }

    fun searchLocationsOnServer(locationName: String): Single<Result<List<SearchLocation>>> =
            locationRepository.searchLocationsOnServer(locationName)
}
