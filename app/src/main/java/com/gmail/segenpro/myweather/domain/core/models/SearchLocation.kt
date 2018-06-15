package com.gmail.segenpro.myweather.domain.core.models

data class SearchLocation(
        val id: Long,
        val name: String,
        val region: String,
        val country: String,
        val latitudeInDegrees: Float,
        val longitudeInDegrees: Float,
        val url: String
)
