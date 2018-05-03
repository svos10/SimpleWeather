package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayDto(
        @SerializedName("maxtemp_c") val maxTemperatureInCelsius: Float?,
        @SerializedName("maxtemp_f") val maxTemperatureInFahrenheit: Float?,
        @SerializedName("mintemp_c") val minTemperatureInCelsius: Float?,
        @SerializedName("mintemp_f") val minTemperatureInFahrenheit: Float?,
        @SerializedName("avgtemp_c") val avgTemperatureInCelsius: Float?,
        @SerializedName("avgtemp_f") val avgTemperatureInFahrenheit: Float?,
        @SerializedName("maxwind_mph") val maxWindInMph: Float?,
        @SerializedName("maxwind_kph") val maxWindInKph: Float?,
        @SerializedName("totalprecip_mm") val totalPrecipitationInMillimeters: Float?,
        @SerializedName("totalprecip_in") val totalPrecipitationInInches: Float?,
        @SerializedName("avgvis_km") val avgVisibilityInKm: Float?,
        @SerializedName("avgvis_miles") val avgVisibilityInMiles: Float?,
        @SerializedName("avghumidity") val avgHumidityInPercentage: Int?,
        @SerializedName("condition") val conditionDto: ConditionDto?,
        @SerializedName("uv") val ultravioletIndex: Float?
)
