package com.gmail.segenpro.myweather.data.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val weatherException: WeatherException, val isShown: Boolean = false) : Result<T>()
}