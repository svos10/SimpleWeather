package com.gmail.segenpro.myweather.domain.core.models

data class Location(
        val id: Long = 0,
        val name: String = "",
        val region: String = "",
        val country: String = "",
        val latitudeInDegrees: Float = 0f,
        val longitudeInDegrees: Float = 0f,
        val timeZone: String = "",
        val localtimeEpoch: Int = 0,
        val localtime: String = ""
)
