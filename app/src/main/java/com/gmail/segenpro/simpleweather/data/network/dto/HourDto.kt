package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class HourDto(
        @SerializedName("time_epoch") val timeEpoch: Int?,
        @SerializedName("time") val time: String?,
        @SerializedName("temp_c") val tempInCelsius: Float?,
        @SerializedName("temp_f") val tempInFahrenheit: Float?,
        @SerializedName("wind_mph") val maxWindInMph: Float?,
        @SerializedName("wind_kph") val maxWindInKph: Float?,
        @SerializedName("wind_degree") val windDirectionInDegrees: Int?,
        @SerializedName("wind_dir") val windDirection: String?,
        @SerializedName("pressure_mb") val pressureInMb: Float?,
        @SerializedName("pressure_in") val pressureInInches: Float?,
        @SerializedName("precip_mm") val precipitationInMillimeters: Float?,
        @SerializedName("precip_in") val precipitationInInches: Float?,
        @SerializedName("humidity") val humidityInPercentage: Int?,
        @SerializedName("cloud") val cloudInPercentage: Int?,
        @SerializedName("feelslike_c") val feelsLikeTempInCelsius: Float?,
        @SerializedName("feelslike_f") val feelsLikeTempInFahrenheit: Float?,
        @SerializedName("windchill_c") val windchillTempInCelsius: Float?,
        @SerializedName("windchill_f") val windchillTempInFahrenheit: Float?,
        @SerializedName("heatindex_c") val heatIndexInCelsius: Float?,
        @SerializedName("heatindex_f") val heatIndexInFahrenheit: Float?,
        @SerializedName("dewpoint_c") val dewPointInCelsius: Float?,
        @SerializedName("dewpoint_f") val dewPointInFahrenheit: Float?,
        @SerializedName("will_it_rain") val willItRain: Int?,
        @SerializedName("will_it_snow") val willItSnow: Int?,
        @SerializedName("is_day") val isDay: Int?,
        @SerializedName("vis_km") val visibilityInKm: Float?,
        @SerializedName("vis_miles") val visibilityInMiles: Float?,
        @SerializedName("condition") val conditionDto: ConditionDto?
) : BaseDto {

    override fun isValid(): Boolean = timeEpoch != null
            && time != null
            && tempInCelsius != null
            && tempInFahrenheit != null
            && maxWindInMph != null
            && maxWindInKph != null
            && windDirectionInDegrees != null
            && windDirection != null
            && pressureInMb != null
            && pressureInInches != null
            && precipitationInMillimeters != null
            && precipitationInInches != null
            && humidityInPercentage != null
            && cloudInPercentage != null
            && feelsLikeTempInCelsius != null
            && feelsLikeTempInFahrenheit != null
            && windchillTempInCelsius != null
            && windchillTempInFahrenheit != null
            && heatIndexInCelsius != null
            && heatIndexInFahrenheit != null
            && dewPointInCelsius != null
            && dewPointInFahrenheit != null
            && willItRain != null
            && willItSnow != null
            && isDay != null
            && visibilityInKm != null
            && visibilityInMiles != null
            && conditionDto?.isValid() ?: false
}
