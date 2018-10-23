package com.gmail.segenpro.simpleweather.domain.core.models

import java.io.Serializable

data class SearchLocation(
        val id: Long,
        val name: String,
        val region: String,
        val country: String,
        val latitudeInDegrees: Float,
        val longitudeInDegrees: Float,
        val url: String
) : Serializable
