package com.gmail.segenpro.simpleweather.domain.core.models

data class Astro(
        val sunrise: String = "",
        val sunset: String = "",
        val moonrise: String = "",
        val moonset: String = "",
        val moonPhase: String = "",
        val moonIllumination: String = ""
)
