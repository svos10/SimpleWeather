package com.gmail.segenpro.myweather.data

import android.content.Context
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.data.network.ErrorType
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.WeatherException
import com.gmail.segenpro.myweather.data.network.dto.BaseDto
import com.gmail.segenpro.myweather.data.network.dto.ErrorWrapperDto
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException

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

fun <T> T.asResult(): Result<T> {
    return Result.Success(this)
}

fun <T> WeatherException.asErrorResult(): Result<T> {
    return Result.Error(this)
}
