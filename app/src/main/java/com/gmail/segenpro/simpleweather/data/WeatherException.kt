package com.gmail.segenpro.simpleweather.data

enum class ErrorType {
    NETWORK_UNAVAILABLE, SERVER_ERROR, SERVER_DATA_ERROR, LOCATION_NOT_SELECTED
}

data class WeatherException(val errorType: ErrorType,
                            val errorCode: Int = 0,
                            val description: String = "") : RuntimeException()
