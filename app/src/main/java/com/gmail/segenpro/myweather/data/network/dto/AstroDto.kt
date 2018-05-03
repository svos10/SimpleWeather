package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class AstroDto(
        @SerializedName("sunrise") val sunrise: String?,
        @SerializedName("sunset") val sunset: String?,
        @SerializedName("moonrise") val moonrise: String?,
        @SerializedName("moonset") val moonset: String?
)
