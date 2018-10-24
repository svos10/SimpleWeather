package com.gmail.segenpro.simpleweather.data.repositories.weather

import android.content.Context
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.gmail.segenpro.simpleweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.simpleweather.data.retrofitResponseToResult
import com.gmail.segenpro.simpleweather.domain.ForecastRepository
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastDataRepository @Inject constructor(private val context: Context,
                                                 private val gson: Gson,
                                                 private val weatherService: WeatherService,
                                                 private val forecastMapper: Mapper<ForecastResponseDto, Forecast>) :
        ForecastRepository {

    override fun getForecast(locationName: String, daysCount: Int): Single<Result<Forecast>> =
            weatherService.getForecast(locationName, daysCount)
                    .retrofitResponseToResult(context, gson)
                    .map {
                        when (it) {
                            is Result.Success -> forecastMapper.map(it.data).asResult()
                            is Result.Error -> it.weatherException.asErrorResult()
                        }
                    }
}
