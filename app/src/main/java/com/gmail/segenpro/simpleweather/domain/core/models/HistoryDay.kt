package com.gmail.segenpro.simpleweather.domain.core.models

data class HistoryDay(
        val responseLocaltimeEpoch: Int = 0,
        val responseLocaltime: String = "",
        val dateEpoch: Int = 0,
        val date: String = "",
        val location: Location = Location(),
        val condition: Condition = Condition(),
        val astro: Astro = Astro(),
        val hours: List<Hour> = ArrayList(),
        val maxTemperatureInCelsius: Float = 0f,
        val maxTemperatureInFahrenheit: Float = 0f,
        val minTemperatureInCelsius: Float = 0f,
        val minTemperatureInFahrenheit: Float = 0f,
        val avgTemperatureInCelsius: Float = 0f,
        val avgTemperatureInFahrenheit: Float = 0f,
        val maxWindInMph: Float = 0f,
        val maxWindInMetersPerSecond: Float = 0f,
        val totalPrecipitationInMillimeters: Float = 0f,
        val totalPrecipitationInInches: Float = 0f,
        val avgVisibilityInKm: Float = 0f,
        val avgVisibilityInMiles: Float = 0f,
        val avgHumidityInPercentage: Int = 0,
        val ultravioletIndex: Float = 0f
)
