package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
        @SerializedName("location") val locationDto: LocationDto?,
        @SerializedName("current") val currentDto: CurrentDto?,
        @SerializedName("forecast") val forecastDaysDto: ForecastDaysDto?
) : BaseResponseDto {

    override fun isValid(): Boolean = locationDto?.isValid() ?: false
            && currentDto?.isValid() ?: false
            && forecastDaysDto?.isValid() ?: false
}
