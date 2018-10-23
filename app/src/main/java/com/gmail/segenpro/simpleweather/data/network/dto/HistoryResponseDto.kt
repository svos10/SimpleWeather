package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class HistoryResponseDto(
        @SerializedName("location") val locationDto: LocationDto?,
        @SerializedName("forecast") val historyDaysDto: HistoryDaysDto?
) : BaseResponseDto {

    override fun isValid(): Boolean = locationDto?.isValid() ?: false
            && historyDaysDto?.isValid() ?: false
}
