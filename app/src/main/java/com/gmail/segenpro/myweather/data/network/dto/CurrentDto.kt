package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName


data class CurrentDto(
        @SerializedName("last_updated") val lastUpdated: String?,
        @SerializedName("last_updated_epoch") val lastUpdatedEpoch: Int?,
        @SerializedName("temp_c") val temperatureInCelsius: Float?,
        @SerializedName("temp_f") val temperatureInFahrenheit: Float?,
        @SerializedName("feelslike_c") val feelingTemperatureInCelsius: Float?,
        @SerializedName("feelslike_f") val feelingTemperatureInFahrenheit: Float?,
        @SerializedName("condition") val conditionDto: ConditionDto?,
        @SerializedName("wind_mph") val windInMph: Float?,
        @SerializedName("wind_kph") val windInKph: Float?,
        @SerializedName("wind_degree") val windDirectionInDegrees: Int?,
        @SerializedName("wind_dir") val windDirection: String?,
        @SerializedName("pressure_mb") val pressureInMillibars: Float?,
        @SerializedName("pressure_in") val pressureInInches: Float?,
        @SerializedName("precip_mm") val precipitationInMillimeters: Float?,
        @SerializedName("precip_in") val precipitationInInches: Float?,
        @SerializedName("humidity") val humidityInPercentage: Int?,
        @SerializedName("cloud") val cloudInPercentage: Int?,
        @SerializedName("is_day") val isDay: Int?,
        @SerializedName("vis_km") val visibilityInKm: Float?,
        @SerializedName("vis_miles") val visibilityInMiles: Float?
)