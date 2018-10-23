package com.gmail.segenpro.simpleweather.data.network.dto

import com.gmail.segenpro.simpleweather.data.isDtoListValid
import com.google.gson.annotations.SerializedName

data class HistoryDaysDto(
        @SerializedName("forecastday") val historyDays: List<HistoryDayDetailsDto>?
) : BaseDto {

    override fun isValid(): Boolean = historyDays?.run {
                !this.isEmpty() && isDtoListValid()
            } ?: false
}
