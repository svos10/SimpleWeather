package com.gmail.segenpro.myweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class SearchLocationDto(
        @SerializedName("id") val id: Long?,
        @SerializedName("name") val name: String?,
        @SerializedName("region") val region: String?,
        @SerializedName("country") val country: String?,
        @SerializedName("lat") val latitudeInDegrees: Float?,
        @SerializedName("lon") val longitudeInDegrees: Float?,
        @SerializedName("url") val url: String?
) : BaseDto {

    override fun isValid(): Boolean = id != null
            && name != null
            && region != null
            && country != null
            && latitudeInDegrees != null
            && longitudeInDegrees != null
            && url != null
}
