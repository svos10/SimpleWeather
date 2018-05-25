package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
        @SerializedName("location") val locationDto: LocationDto?,
        @SerializedName("current") val currentDto: CurrentDto?,
        @SerializedName("forecast") val forecastDaysDto: ForecastDaysDto?,
        @SerializedName("error") val errorDto: ErrorDto?
)
