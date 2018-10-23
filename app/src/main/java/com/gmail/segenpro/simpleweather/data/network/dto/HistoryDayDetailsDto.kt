package com.gmail.segenpro.simpleweather.data.network.dto

import com.gmail.segenpro.simpleweather.data.isDtoListValid
import com.google.gson.annotations.SerializedName

data class HistoryDayDetailsDto(
        @SerializedName("date") val date: String?,
        @SerializedName("date_epoch") val dateEpoch: Int?,
        @SerializedName("day") val day: DayDto?,
        @SerializedName("astro") val astro: AstroDto?,
        @SerializedName("hour") val hours: List<HourDto>?
) : BaseDto {

    override fun isValid(): Boolean = date != null
            && dateEpoch != null
            && day?.isValid() ?: false
            && astro?.isValid() ?: false
            && hours.isDtoListValid()
}
