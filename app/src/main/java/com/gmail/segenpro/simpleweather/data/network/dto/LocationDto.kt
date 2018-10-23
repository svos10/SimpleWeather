package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
        @SerializedName("lat") val latitudeInDegrees: Float?,
        @SerializedName("lon") val longitudeInDegrees: Float?,
        @SerializedName("name") val name: String?,
        @SerializedName("region") val region: String?,
        @SerializedName("country") val country: String?,
        @SerializedName("tz_id") val timeZone: String?,
        @SerializedName("localtime_epoch") val localtimeEpoch: Int?,
        @SerializedName("localtime") val localtime: String?
) : BaseDto {

    override fun isValid(): Boolean = latitudeInDegrees != null
            && longitudeInDegrees != null
            && name != null
            && region != null
            && country != null
            && timeZone != null
            && localtimeEpoch != null
            && localtime != null
}
