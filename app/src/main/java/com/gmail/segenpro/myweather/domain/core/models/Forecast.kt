package com.gmail.segenpro.myweather.domain.core.models

data class Forecast(val temperatureInCelsius: Float = 0f,
                    val icon: String = "",
                    val description: String = "",
                    val windInMetersPerSecond: Float = 0f
)
