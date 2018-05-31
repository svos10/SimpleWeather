package com.gmail.segenpro.myweather.data.repositories.weather

import android.content.Context
import com.gmail.segenpro.myweather.data.asErrorResult
import com.gmail.segenpro.myweather.data.asResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.WeatherService
import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.myweather.data.network.mappers.Mapper
import com.gmail.segenpro.myweather.data.retrofitResponseToResult
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.domain.weather.WeatherRepository
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataRepository @Inject constructor(private val context: Context,
                                                private val weatherService: WeatherService,
                                                private val forecastMapper: Mapper<ForecastResponseDto, Forecast>,
                                                private val gson: Gson) : WeatherRepository {

    override fun getForecast(locationName: String, daysCount: Int): Single<Result<Forecast>> {
        return weatherService.getForecast(locationName, daysCount)
                .retrofitResponseToResult(context, gson)
                .map {
                    when (it) {
                        is Result.Success -> forecastMapper.map(it.data).asResult()
                        is Result.Error -> it.weatherException.asErrorResult()
                    }
                }
    }

    //todo написать функцию getHistory
}
