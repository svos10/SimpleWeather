package com.gmail.segenpro.myweather.data.network

import com.gmail.segenpro.myweather.data.WeatherException

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val weatherException: WeatherException, val isShown: Boolean = false) : Result<T>()
}
