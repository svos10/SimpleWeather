package com.gmail.segenpro.myweather.domain.weather

import javax.inject.Inject
import javax.inject.Singleton

private const val FORECAST_CITY = "Izhevsk"
private const val FORECAST_DAYS = 3

@Singleton
class WeatherInteractor @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun getForecast() = weatherRepository.getForecast(FORECAST_CITY, FORECAST_DAYS)
}
