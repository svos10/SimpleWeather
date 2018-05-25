package com.gmail.segenpro.myweather.domain.core.models

data class Forecast(val temperatureInCelsius: Float,
                    val icon: String,
                    val description: String,
                    val windInKph: Float)

