package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorWrapperDto(
        @SerializedName("error") val errorDto: ErrorDto?
)
