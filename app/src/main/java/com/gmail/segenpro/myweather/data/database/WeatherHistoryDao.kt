package com.gmail.segenpro.myweather.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.gmail.segenpro.myweather.data.database.entities.*
import com.gmail.segenpro.myweather.data.database.pojo.HistoryEntitiesWrapper
import com.gmail.segenpro.myweather.data.database.pojo.HistoryObject

@Dao
abstract class WeatherHistoryDao {

    @Query("SELECT * FROM AstroEntity WHERE id = :id")
    abstract fun getAstroEntity(id: Long): AstroEntity

    @Query("SELECT * FROM ConditionEntity WHERE code IN (:codes)")
    abstract fun getConditionEntities(codes: List<Int>): List<ConditionEntity>

    @Query("SELECT * FROM LocationEntity WHERE id = :id")
    abstract fun getLocationEntity(id: Long): LocationEntity?

    @Query("SELECT id FROM LocationEntity WHERE name = :name AND region = :region AND country = :country")
    abstract fun getLocationId(name: String, region: String, country: String): Long?

    @Query("SELECT * FROM HourEntity WHERE id IN (:ids)")
    abstract fun getHourEntities(ids: List<Long>): List<HourEntity>

    @Query("SELECT * FROM HistoryDayEntity WHERE location_id = :location_id ORDER BY date_epoch DESC")
    abstract fun getHistoryObjects(location_id: Long): List<HistoryObject>

    @Query("SELECT count(*) FROM HistoryDayEntity WHERE location_id = :location_id")
    abstract fun getHistoryCount(location_id: Long): Int

    @Query("SELECT count(*) FROM HistoryDayEntity WHERE location_id = :location_id AND date_epoch = :date_epoch")
    abstract fun getHistoryCount(location_id: Long, date_epoch: Int): Int

    @Query("SELECT date FROM HistoryDayEntity WHERE location_id = :location_id ORDER BY date_epoch DESC LIMIT :daysCount")
    abstract fun getRecentDays(location_id: Long, daysCount: Int): List<String>

    @Insert(onConflict = REPLACE)
    abstract fun insert(locationEntity: LocationEntity): Long

    @Insert(onConflict = REPLACE)
    abstract fun insert(conditionEntity: ConditionEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insertConditions(conditionEntities: List<ConditionEntity>)

    @Insert
    abstract fun insert(historyDayEntity: HistoryDayEntity): Long

    @Insert
    abstract fun insert(astroEntity: AstroEntity)

    @Insert
    abstract fun insertHours(hourEntities: List<HourEntity>)

    @Query("UPDATE LocationEntity SET latitude = :latitudeInDegrees, longitude = :longitudeInDegrees, time_zone = :timeZone WHERE id = :locationId")
    abstract fun updateLocationById(locationId: Long, latitudeInDegrees: Float, longitudeInDegrees: Float,
                                    timeZone: String)

    @Query("DELETE FROM HistoryDayEntity WHERE id IN "
            + "(SELECT id FROM HistoryDayEntity WHERE location_id = :locationId ORDER BY date_epoch LIMIT :countForDeletion)")
    abstract fun deleteOldHistory(locationId: Long, countForDeletion: Int)

    @Transaction
    open fun getHistory(locationEntity: LocationEntity): List<HistoryObject> {
        val locationId = getLocationId(locationEntity.name, locationEntity.region, locationEntity.country)
                ?: return ArrayList()
        return getHistoryObjects(locationId)
    }

    @Transaction
    open fun insertHistoryDay(historyEntitiesWrapper: HistoryEntitiesWrapper) {
        val locationEntity = historyEntitiesWrapper.locationEntity
        val locationId: Long = getLocationId(locationEntity.name, locationEntity.region, locationEntity.country)
                ?: 0
        if (getHistoryCount(locationId, historyEntitiesWrapper.historyDayEntity.dateEpoch) == 1) return

        if (locationId == 0L) {
            return
        } else {
            updateLocationById(locationId, locationEntity.latitudeInDegrees, locationEntity.longitudeInDegrees,
                    locationEntity.timeZone)
        }

        insert(historyEntitiesWrapper.dayConditionEntity)
        insertConditions(historyEntitiesWrapper.hourConditionEntities)
        val historyId = insert(historyEntitiesWrapper.historyDayEntity.copy(locationId = locationId))
        insert(historyEntitiesWrapper.astroEntity.copy(historyId = historyId))
        val newHourEntities = ArrayList<HourEntity>().apply {
            historyEntitiesWrapper.hourEntities.forEach { this.add(it.copy(historyId = historyId)) }
        }
        insertHours(newHourEntities)
    }

    @Transaction
    open fun deleteOldHistory(locationEntity: LocationEntity, maxKeepCount: Int) {
        val locationId = getLocationId(locationEntity.name, locationEntity.region, locationEntity.country)
                ?: return
        val historyCount = getHistoryCount(locationId)
        if (historyCount <= maxKeepCount) return
        deleteOldHistory(locationId, historyCount - maxKeepCount)
    }
}
