package com.gmail.segenpro.myweather.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class LocationEntity(
        @PrimaryKey
        val id: Long,
        val name: String,
        val region: String,
        val country: String,
        @ColumnInfo(name = "latitude")
        val latitudeInDegrees: Float,
        @ColumnInfo(name = "longitude")
        val longitudeInDegrees: Float,
        @ColumnInfo(name = "time_zone")
        val timeZone: String
)
