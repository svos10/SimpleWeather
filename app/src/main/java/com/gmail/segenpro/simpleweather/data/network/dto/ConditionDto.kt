package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ConditionDto(
        @SerializedName("text") val text: String?,
        @SerializedName("icon") val icon: String?,
        @SerializedName("code") val code: Int?
) : BaseDto {

    override fun isValid(): Boolean = text != null && icon != null && code != null
}
