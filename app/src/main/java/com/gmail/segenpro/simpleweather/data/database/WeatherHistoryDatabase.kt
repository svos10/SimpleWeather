package com.gmail.segenpro.simpleweather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.gmail.segenpro.simpleweather.data.database.entities.*

@Database(entities = [AstroEntity::class, ConditionEntity::class, LocationEntity::class,
    HourEntity::class, HistoryDayEntity::class], version = 1, exportSchema = false)
abstract class WeatherHistoryDatabase : RoomDatabase() {

    abstract fun weatherHistoryDao(): WeatherHistoryDao
}
