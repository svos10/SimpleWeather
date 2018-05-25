package com.gmail.segenpro.myweather.data.network

enum class ErrorType {
    NETWORK_UNAVAILABLE, SERVER_ERROR, APP_ERROR
}

data class WeatherException(val errorType: ErrorType,
                            val errorCode: Int = 0,
                            val description: String = "") : RuntimeException()
