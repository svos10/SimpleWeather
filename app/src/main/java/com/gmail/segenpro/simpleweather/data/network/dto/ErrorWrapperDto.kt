package com.gmail.segenpro.simpleweather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorWrapperDto(
        @SerializedName("error") val errorDto: ErrorDto?
) : BaseDto {

    override fun isValid(): Boolean = errorDto?.isValid() ?: false
}
