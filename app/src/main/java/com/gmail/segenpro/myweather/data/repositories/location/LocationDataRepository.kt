package com.gmail.segenpro.myweather.data.repositories.location

import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.asResult
import com.gmail.segenpro.myweather.data.ErrorType
import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.data.database.entities.LocationEntity
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.dto.SearchLocationDto
import com.gmail.segenpro.myweather.data.network.dto.SearchLocationsResponseDto
import com.gmail.segenpro.myweather.data.repositories.BaseDataRepository
import com.gmail.segenpro.myweather.data.retrofitResponseToResult
import com.gmail.segenpro.myweather.domain.LocationRepository
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataRepository @Inject constructor(
        private val dtoToSearchLocationMapper: Mapper<SearchLocationDto, SearchLocation>,
        private val searchLocationToLocationEntityMapper: Mapper<SearchLocation, LocationEntity>,
        private val toLocationModelMapper: Mapper<LocationEntity, Location>
) : BaseDataRepository(), LocationRepository {

    override fun getLocationFromDb(locationId: Long): Single<Result<Location>> =
            Single.fromCallable {
                weatherHistoryDao.getLocationEntity(locationId)?.let {
                    toLocationModelMapper.map(it).asResult()
                } ?: WeatherException(ErrorType.LOCATION_NOT_SELECTED).asErrorResult()
            }
                    .subscribeOn(Schedulers.io())

    override fun putLocationToDb(searchLocation: SearchLocation): Completable =
            Completable.fromAction {
                weatherHistoryDao.upsert(searchLocationToLocationEntityMapper.map(searchLocation))
            }

    override fun searchLocationsAtServer(locationName: String): Single<Result<List<SearchLocation>>> =
            weatherService.searchLocation(locationName)
                    .map { SearchLocationsResponseDto(it) }
                    .retrofitResponseToResult(context, gson)
                    .map {
                        when (it) {
                            is Result.Success -> it.data.searchLocationsDto
                                    .asSequence()
                                    .map { dto -> dtoToSearchLocationMapper.map(dto) }
                                    .filter { searchLocation -> searchLocation.name.startsWith(locationName, true) }
                                    .toList()
                                    .asResult()

                            is Result.Error -> it.weatherException.asErrorResult()
                        }
                    }
}
