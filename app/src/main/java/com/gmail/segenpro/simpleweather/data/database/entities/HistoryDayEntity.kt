package com.gmail.segenpro.simpleweather.data.database.entities

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.RESTRICT

@Entity(foreignKeys = [ForeignKey(entity = ConditionEntity::class, parentColumns = ["code"], childColumns = ["condition_code"]),
    ForeignKey(entity = LocationEntity::class, parentColumns = ["id"], childColumns = ["location_id"],
            onDelete = RESTRICT, onUpdate = RESTRICT)],
        indices = [Index(value = ["location_id", "date_epoch"], unique = true)])// индекс добавлен исключительно для unique
data class HistoryDayEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "condition_code")
        val conditionCode: Int,
        @ColumnInfo(name = "location_id")
        val locationId: Long,
        @ColumnInfo(name = "date_epoch")
        val dateEpoch: Int,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "max_temp_c")
        val minTempInCelsius: Float,
        @ColumnInfo(name = "max_temp_f")
        val maxTempInFahrenheit: Float,
        @ColumnInfo(name = "min_temp_c")
        val maxTempInCelsius: Float,
        @ColumnInfo(name = "min_temp_f")
        val minTempInFahrenheit: Float,
        @ColumnInfo(name = "avg_temp_c")
        val avgTempInCelsius: Float,
        @ColumnInfo(name = "avg_temp_f")
        val avgTempInFahrenheit: Float,
        @ColumnInfo(name = "max_wind_mph")
        val maxWindInMph: Float,
        @ColumnInfo(name = "max_wind_meters_per_sec")
        val maxWindInMetersPerSecond: Float,
        @ColumnInfo(name = "total_precip_mm")
        val totalPrecipitationInMillimeters: Float,
        @ColumnInfo(name = "total_precip_in")
        val totalPrecipitationInInches: Float,
        @ColumnInfo(name = "avg_vis_km")
        val avgVisibilityInKm: Float,
        @ColumnInfo(name = "avg_vis_miles")
        val avgVisibilityInMiles: Float,
        @ColumnInfo(name = "avg_hum_percent")
        val avgHumidityInPercentage: Int,
        @ColumnInfo(name = "uv_index")
        val ultravioletIndex: Float,
        @ColumnInfo(name = "response_localtime_epoch")
        val responseLocaltimeEpoch: Int,
        @ColumnInfo(name = "response_localtime")
        val responseLocaltime: String
)
