package com.gmail.segenpro.simpleweather.data.repositories.location

import android.content.Context
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.data.database.WeatherHistoryDao
import com.gmail.segenpro.simpleweather.data.database.entities.LocationEntity
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.gmail.segenpro.simpleweather.data.network.dto.SearchLocationDto
import com.gmail.segenpro.simpleweather.data.network.dto.SearchLocationsResponseDto
import com.gmail.segenpro.simpleweather.data.retrofitResponseToResult
import com.gmail.segenpro.simpleweather.domain.LocationRepository
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataRepository @Inject constructor(private val context: Context,
                                                 private val gson: Gson,
                                                 private val weatherService: WeatherService,
                                                 private val weatherHistoryDao: WeatherHistoryDao,
                                                 private val dtoToSearchLocationMapper: Mapper<SearchLocationDto, SearchLocation>,
                                                 private val searchLocationToLocationEntityMapper: Mapper<SearchLocation, LocationEntity>,
                                                 private val toLocationModelMapper: Mapper<LocationEntity, Location>
) : LocationRepository {

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
