package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
        @SerializedName("code") val code: Int?,
        @SerializedName("message") val text: String?
) : BaseDto {

    override fun isValid(): Boolean = code != null && text != null
}
