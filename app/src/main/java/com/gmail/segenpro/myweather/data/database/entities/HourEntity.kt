package com.gmail.segenpro.myweather.data.database.entities

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.ForeignKey.RESTRICT

@Entity(foreignKeys = [ForeignKey(entity = HistoryDayEntity::class, parentColumns = ["id"], childColumns = ["history_id"],
        onDelete = CASCADE, onUpdate = RESTRICT),
    ForeignKey(entity = ConditionEntity::class, parentColumns = ["code"], childColumns = ["condition_code"])],
        indices = [Index(value = ["history_id", "time_epoch"], unique = true)])// индекс добавлен исключительно для unique
data class HourEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "history_id")
        val historyId: Long,
        @ColumnInfo(name = "condition_code")
        val conditionCode: Int,
        @ColumnInfo(name = "time_epoch")
        val timeEpoch: Int,
        @ColumnInfo(name = "time")
        val time: String,
        @ColumnInfo(name = "temp_c")
        val tempInCelsius: Float,
        @ColumnInfo(name = "temp_f")
        val tempInFahrenheit: Float,
        @ColumnInfo(name = "max_wind_mph")
        val maxWindInMph: Float,
        @ColumnInfo(name = "max_wind_meters_per_sec")
        val maxWindInMetersPerSecond: Float,
        @ColumnInfo(name = "wind_degree")
        val windDirectionInDegrees: Int,
        @ColumnInfo(name = "wind_dir")
        val windDirection: String,
        @ColumnInfo(name = "pressure_mb")
        val pressureInMb: Float,
        @ColumnInfo(name = "pressure_in")
        val pressureInInches: Float,
        @ColumnInfo(name = "precip_mm")
        val precipitationInMillimeters: Float,
        @ColumnInfo(name = "precip_in")
        val precipitationInInches: Float,
        @ColumnInfo(name = "hum_percent")
        val humidityInPercentage: Int,
        @ColumnInfo(name = "cloud")
        val cloudInPercentage: Int,
        @ColumnInfo(name = "feelslike_c")
        val feelsLikeTempInCelsius: Float,
        @ColumnInfo(name = "feelslike_f")
        val feelsLikeTempInFahrenheit: Float,
        @ColumnInfo(name = "windchill_c")
        val windchillTempInCelsius: Float,
        @ColumnInfo(name = "windchill_f")
        val windchillTempInFahrenheit: Float,
        @ColumnInfo(name = "heatindex_c")
        val heatIndexInCelsius: Float,
        @ColumnInfo(name = "heatindex_f")
        val heatIndexInFahrenheit: Float,
        @ColumnInfo(name = "dewpoint_c")
        val dewPointInCelsius: Float,
        @ColumnInfo(name = "dewpoint_f")
        val dewPointInFahrenheit: Float,
        @ColumnInfo(name = "will_it_rain")
        val willItRain: Boolean,
        @ColumnInfo(name = "will_it_snow")
        val willItSnow: Boolean,
        @ColumnInfo(name = "is_day")
        val isDay: Boolean,
        @ColumnInfo(name = "vis_km")
        val visibilityInKm: Float,
        @ColumnInfo(name = "vis_miles")
        val visibilityInMiles: Float
)
