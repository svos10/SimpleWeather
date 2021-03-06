package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class AstroDto(
        @SerializedName("sunrise") val sunrise: String?,
        @SerializedName("sunset") val sunset: String?,
        @SerializedName("moonrise") val moonrise: String?,
        @SerializedName("moonset") val moonset: String?,
        @SerializedName("moon_phase") val moonPhase: String?,
        @SerializedName("moon_illumination") val moonIllumination: String?
) : BaseDto {

    override fun isValid(): Boolean = sunrise != null
            && sunset != null
            && moonrise != null
            && moonset != null
}
