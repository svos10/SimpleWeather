package com.gmail.segenpro.myweather.domain.location

import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.data.ErrorType
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.CURRENT_LOCATION_ID_NOT_SET
import com.gmail.segenpro.myweather.domain.core.Repository
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import com.gmail.segenpro.myweather.domain.LocationRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LocationInteractor @Inject constructor(private val locationRepository: LocationRepository) {

    @Inject
    @field:Named("currentLocationId")
    lateinit var currentLocationIdRepository: Repository<Long>

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

    fun searchLocationsAtServer(locationName: String): Single<Result<List<SearchLocation>>> =
            locationRepository.searchLocationsAtServer(locationName)
}
