package com.gmail.segenpro.myweather.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.ForeignKey.RESTRICT
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = HistoryDayEntity::class, parentColumns = ["id"], childColumns = ["history_id"],
        onDelete = CASCADE, onUpdate = RESTRICT)])
data class AstroEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "history_id")
        val historyId: Long,
        val sunrise: String,
        val sunset: String,
        val moonrise: String,
        val moonset: String,
        @ColumnInfo(name = "moon_phase")
        val moonPhase: String,
        @ColumnInfo(name = "moon_illumination")
        val moonIllumination: String
)
