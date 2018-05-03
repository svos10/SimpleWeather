package com.gmail.segenpro.myweather.data.network

import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    fun getForecast(@Query("q") locationName: String, @Query("days") daysCount: Int): Observable<ForecastResponseDto>
}