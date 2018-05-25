package com.gmail.segenpro.myweather.data.repositories.weather

import com.gmail.segenpro.myweather.data.network.ErrorType
import com.gmail.segenpro.myweather.data.network.WeatherException
import com.gmail.segenpro.myweather.data.network.WeatherService
import com.gmail.segenpro.myweather.data.network.dto.ErrorWrapperDto
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.domain.weather.WeatherRepository
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataRepository @Inject constructor(private val weatherService: WeatherService,
                                                private val gson: Gson) : WeatherRepository {

    override fun getForecast(locationName: String, daysCount: Int): Single<Forecast> {
        return weatherService.getForecast(locationName, daysCount)
                .onErrorResumeNext({ throwable ->
                    when (throwable) {
                        is IOException -> Single.error(WeatherException(ErrorType.NETWORK_UNAVAILABLE))
                        is HttpException -> {
                            val jsonString = throwable.response()?.errorBody()?.string() ?: ""
                            val errorWrapperDto: ErrorWrapperDto? = gson.fromJson(jsonString, ErrorWrapperDto::class.java)
                            var weatherException = WeatherException(ErrorType.SERVER_ERROR)
                            if (errorWrapperDto?.errorDto?.code != null && errorWrapperDto.errorDto.text != null) {
                                weatherException = WeatherException(ErrorType.SERVER_ERROR,
                                        errorWrapperDto.errorDto.code, errorWrapperDto.errorDto.text)
                            }
                            Single.error(weatherException)
                        }
                        else -> Single.error(WeatherException(ErrorType.APP_ERROR))
                    }
                })
                .map { Forecast(0f, "", "", 0f) }//todo-sem delete
    }
}
