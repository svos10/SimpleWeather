package com.gmail.segenpro.simpleweather.data.network

import com.gmail.segenpro.simpleweather.data.WeatherException

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val weatherException: WeatherException) : Result<T>()
}
