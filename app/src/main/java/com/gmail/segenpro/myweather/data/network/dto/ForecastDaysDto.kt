package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDaysDto(
        @SerializedName("forecastday") val forecastDays: List<ForecastDayDetailsDto>?
)
