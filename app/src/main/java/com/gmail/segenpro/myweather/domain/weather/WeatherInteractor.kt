package com.gmail.segenpro.myweather.domain.weather

import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.asResult
import com.gmail.segenpro.myweather.data.ErrorType
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.CURRENT_LOCATION_NOT_SET
import com.gmail.segenpro.myweather.domain.Repository
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val FORECAST_DAYS = 3
private const val HISTORY_DAYS_COUNT = 7
private const val HISTORY_MAX_COUNT = 30
private const val DATE_FORMAT = "yyyy-MM-dd"

private val sdf by lazy {
    SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
}

private fun getDaysAgo(daysAgo: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
    return sdf.format(calendar.time)
}

@Singleton
class WeatherInteractor @Inject constructor(private val forecastRepository: ForecastRepository,
                                            private val historyRepository: HistoryRepository) {

    @Inject
    @field:Named("currentLocation")
    lateinit var currentLocationRepository: Repository<Long>

    private fun getHistoryFromServerAndCache(location: Location, daysCount: Int) = (1..daysCount).toObservable()
            .flatMapSingle { historyRepository.getHistoryFromServer(location.name, getDaysAgo(it)) }
            .flatMapCompletable { historyDayResult ->
                when (historyDayResult) {
                    is Result.Success -> historyRepository.putHistoryDayToDb(historyDayResult.data)

                    is Result.Error -> Completable.complete()
                }
            }
            .andThen(historyRepository.deleteOldHistoryFromDb(location, HISTORY_MAX_COUNT))

    fun getCurrentLocation(): Observable<Result<Location>> = currentLocationRepository.observe()
            .flatMapSingle {
                if (it == CURRENT_LOCATION_NOT_SET) {
                    Single.just(WeatherException(ErrorType.LOCATION_NOT_SELECTED).asErrorResult())
                } else {
                    forecastRepository.getLocationFromDb(it)
                }
            }

    fun setCurrentLocation(searchLocation: SearchLocation): Completable =
            currentLocationRepository.setAndObserveSingle(searchLocation.id)
                    .flatMapCompletable { forecastRepository.putLocationToDb(searchLocation) }

    fun searchLocationsAtServer(locationName: String): Single<Result<List<SearchLocation>>> =
            forecastRepository.searchLocationsAtServer(locationName)

    fun getForecast() = getCurrentLocation()
            .flatMapSingle {
                when (it) {
                    is Result.Success -> forecastRepository.getForecast(it.data.name, FORECAST_DAYS)

                    is Result.Error -> Single.just(it.weatherException.asErrorResult())
                }
            }

    fun getHistory(): Observable<Result<List<HistoryDay>>> = getCurrentLocation()
            .flatMapSingle { locationResult ->
                when (locationResult) {
                    is Result.Success ->
                        getHistoryFromServerAndCache(locationResult.data, HISTORY_DAYS_COUNT)
                                .andThen(historyRepository.getHistoryFromDb(locationResult.data)
                                        .map { it.asResult() })
                    is Result.Error -> Single.just(locationResult.weatherException.asErrorResult())
                }
            }
}
