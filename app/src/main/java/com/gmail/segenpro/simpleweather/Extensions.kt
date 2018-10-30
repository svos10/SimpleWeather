package com.gmail.segenpro.simpleweather

import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.data.network.Result

fun <T> T.asResult(): Result<T> = Result.Success(this)

fun <T> WeatherException.asErrorResult(): Result.Error<T> = Result.Error(this)
