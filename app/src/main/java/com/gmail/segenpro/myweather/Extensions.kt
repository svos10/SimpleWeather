package com.gmail.segenpro.myweather

import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.data.network.Result

fun <T> T.asResult(): Result<T> = Result.Success(this)

fun <T> WeatherException.asErrorResult(): Result.Error<T> = Result.Error(this)

fun <T> Result.Error<T>.showError(): Result<T> = this.copy(isShown = true)
