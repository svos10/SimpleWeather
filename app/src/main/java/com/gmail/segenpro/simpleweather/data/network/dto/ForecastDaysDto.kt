package com.gmail.segenpro.simpleweather.data.network.dto

import com.gmail.segenpro.simpleweather.data.isDtoListValid
import com.google.gson.annotations.SerializedName

data class ForecastDaysDto(
        @SerializedName("forecastday") val forecastDays: List<ForecastDayDetailsDto>?
) : BaseDto {

    override fun isValid(): Boolean = forecastDays.isDtoListValid()
}
