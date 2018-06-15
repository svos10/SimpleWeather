package com.gmail.segenpro.myweather.data

import android.content.Context
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.asResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.dto.BaseDto
import com.gmail.segenpro.myweather.data.network.dto.ErrorWrapperDto
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.round

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
                    else -> throw it //todo решить, что лучше: всё перехватывать через WeatherException(ErrorType.APP_ERROR).asErrorResult(), либо throw it
                }
            }
}

fun Float.kilometersPerHourToMetersPerSecond() = this * 1000 / 3600

fun Float.roundingToOneDecimalPlace() = round(this * 10) / 10

fun <T : BaseDto> List<T>?.isDtoListValid(): Boolean {
    this?.forEach {
        if (!it.isValid()) return false
    } ?: return false
    return true
}

fun Int.toBoolean() = this != 0
