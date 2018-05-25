package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName


data class ForecastDayDetailsDto(
        @SerializedName("date") val date: String?,
        @SerializedName("date_epoch") val dateEpoch: Int?,
        @SerializedName("day") val day: DayDto?,
        @SerializedName("astro") val astro: AstroDto?
)