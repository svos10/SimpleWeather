package com.gmail.segenpro.simpleweather.data

import android.content.Context
import com.gmail.segenpro.simpleweather.R
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.dto.BaseDto
import com.gmail.segenpro.simpleweather.data.network.dto.ErrorWrapperDto
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.round

private const val METERS_IN_KILOMETER = 1000
private const val SECONDS_IN_HOUR = 3600
private const val FIRST_FRACTIONAL_DIGIT_FACTOR = 10

fun <T : BaseDto> Single<T>.retrofitResponseToResult(context: Context, gson: Gson): Single<Result<T>> {
    return this
            .map {
                if (it.isValid()) {
                    it.asResult()
                } else {
                    WeatherException(errorType = ErrorType.SERVER_DATA_ERROR,
                            description = context.getString(R.string.incorrect_server_data)).asErrorResult()
                }
            }
            .onErrorReturn {
                //выбранная архитектура обрабатывает только ожидаемые исключения,
                //см. https://rongi.github.io/kotlin-blog/rxjava/rx/2017/08/01/error-handling-in-rxjava.html
                return@onErrorReturn when (it) {
                    is IOException -> WeatherException(ErrorType.NETWORK_UNAVAILABLE).asErrorResult()
                    is HttpException -> {
                        val jsonString = it.response()?.errorBody()?.string() ?: ""
                        val errorWrapperDto: ErrorWrapperDto? = gson.fromJson(jsonString, ErrorWrapperDto::class.java)
                        val weatherException = if (errorWrapperDto != null && errorWrapperDto.isValid()) {
                            // утверждения !! здесь допустимы, т.к. проверка уже сделана вызовом метода errorWrapperDto.isValid()
                            WeatherException(ErrorType.SERVER_ERROR, errorWrapperDto.errorDto!!.code!!,
                                    errorWrapperDto.errorDto.text!!)
                        } else {
                            WeatherException(ErrorType.SERVER_ERROR)
                        }

                        weatherException.asErrorResult()
                    }
                    //при корректно работающей программе сюда не попадаем
                    else -> throw it
                }
            }
}

fun Float.kilometersPerHourToMetersPerSecond() = this * METERS_IN_KILOMETER / SECONDS_IN_HOUR

fun Float.roundingToOneDecimalPlace() = round(this * FIRST_FRACTIONAL_DIGIT_FACTOR) / FIRST_FRACTIONAL_DIGIT_FACTOR

fun <T : BaseDto> List<T>?.isDtoListValid(): Boolean {
    this?.forEach {
        if (!it.isValid()) return false
    } ?: return false
    return true
}

fun Int.toBoolean() = this != 0
