package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDaysDto(
        @SerializedName("forecastday") val forecastDays: List<ForecastDayDetailsDto>?
) : BaseDto {

    override fun isValid(): Boolean {
        forecastDays?.forEach {
            if (!it.isValid()) return false
        } ?: return false
        return true
    }
}

