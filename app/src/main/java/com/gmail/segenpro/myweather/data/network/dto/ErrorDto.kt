package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
        @SerializedName("code") val code: Int?,
        @SerializedName("message") val text: String?
)
