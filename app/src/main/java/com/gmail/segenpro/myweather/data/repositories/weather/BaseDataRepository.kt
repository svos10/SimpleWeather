package com.gmail.segenpro.myweather.data.repositories.weather

import android.content.Context
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.asResult
import com.gmail.segenpro.myweather.data.ErrorType
import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.data.database.WeatherHistoryDao
import com.gmail.segenpro.myweather.data.database.entities.LocationEntity
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.WeatherService
import com.gmail.segenpro.myweather.data.network.dto.SearchLocationDto
import com.gmail.segenpro.myweather.data.retrofitResponseToResult
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import com.gmail.segenpro.myweather.domain.weather.BaseRepository
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseDataRepository : BaseRepository {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var weatherService: WeatherService

    @Inject
    lateinit var weatherHistoryDao: WeatherHistoryDao

    @Inject
    lateinit var dtoToSearchLocationMapper: Mapper<SearchLocationDto, SearchLocation>

    @Inject
    lateinit var searchLocationToLocationEntityMapper: Mapper<SearchLocation, LocationEntity>

    @Inject
    lateinit var toLocationModelMapper: Mapper<LocationEntity, Location>

    override fun getLocationFromDb(locationId: Long): Single<Result<Location>> =
            Single.fromCallable {
                weatherHistoryDao.getLocationEntity(locationId)?.let {
                    toLocationModelMapper.map(it).asResult()
                } ?: WeatherException(ErrorType.LOCATION_NOT_SELECTED).asErrorResult()
            }
                    .subscribeOn(Schedulers.io())

    override fun putLocationToDb(searchLocation: SearchLocation): Completable =
            Completable.fromAction {
                weatherHistoryDao.insert(searchLocationToLocationEntityMapper.map(searchLocation))
            }

    override fun searchLocationsAtServer(locationName: String): Single<Result<List<SearchLocation>>> =
            weatherService.searchLocation(locationName)
                    .retrofitResponseToResult(context, gson)
                    .map {
                        when (it) {
                            is Result.Success -> it.data
                                    .map { dtoToSearchLocationMapper.map(it) }
                                    .filter { it.name.startsWith(locationName) }
                                    .asResult()

                            is Result.Error -> it.weatherException.asErrorResult()
                        }
                    }
}
