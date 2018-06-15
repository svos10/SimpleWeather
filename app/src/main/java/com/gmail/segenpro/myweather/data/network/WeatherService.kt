package com.gmail.segenpro.myweather.data.network

import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.myweather.data.network.dto.HistoryResponseDto
import com.gmail.segenpro.myweather.data.network.dto.SearchLocationsResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("search.json")
    fun searchLocation(@Query("q") locationName: String): Single<SearchLocationsResponseDto>

    @GET("forecast.json")
    fun getForecast(@Query("q") locationName: String, @Query("days") daysCount: Int): Single<ForecastResponseDto>

    @GET("history.json")
    fun getHistory(@Query("q") locationName: String, @Query("dt") historyDate: String): Single<HistoryResponseDto>
}
