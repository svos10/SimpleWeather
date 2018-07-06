package com.gmail.segenpro.myweather.data.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
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

    @Query("SELECT * FROM HistoryDayEntity WHERE location_id = :locationId ORDER BY date_epoch DESC")
    abstract fun getHistoryObjects(locationId: Long): List<HistoryObject>

    @Query("SELECT count(*) FROM HistoryDayEntity WHERE location_id = :locationId")
    abstract fun getHistoryCount(locationId: Long): Int

    @Query("SELECT count(*) FROM HistoryDayEntity WHERE location_id = :locationId AND date_epoch = :date_epoch")
    abstract fun getHistoryCount(locationId: Long, date_epoch: Int): Int

    @Query("SELECT date FROM HistoryDayEntity WHERE location_id = :locationId ORDER BY date_epoch DESC LIMIT :daysCount")
    abstract fun getRecentDays(locationId: Long, daysCount: Int): List<String>

    @Insert(onConflict = IGNORE)
    abstract fun insert(locationEntity: LocationEntity): Long

    @Update(onConflict = IGNORE)
    abstract fun update(locationEntity: LocationEntity)

    @Insert(onConflict = IGNORE)
    abstract fun insert(conditionEntity: ConditionEntity): Long

    @Update(onConflict = IGNORE)
    abstract fun update(conditionEntity: ConditionEntity)

    @Insert(onConflict = IGNORE)
    abstract fun insertConditions(conditionEntities: List<ConditionEntity>)

    @Update(onConflict = IGNORE)
    abstract fun updateConditions(conditionEntities: List<ConditionEntity>)

    @Insert
    abstract fun insert(historyDayEntity: HistoryDayEntity): Long

    @Insert
    abstract fun insert(astroEntity: AstroEntity)

    @Insert
    abstract fun insertHours(hourEntities: List<HourEntity>)

    @Transaction
    open fun upsert(locationEntity: LocationEntity) {
        val id = insert(locationEntity)
        if (id != -1L) return
        if (locationEntity.timeZone.isEmpty()) {
            val timezone = getLocationEntity(locationEntity.id)?.timeZone ?: ""
            val updatedLocationEntity = locationEntity.copy(timeZone = timezone)
            update(updatedLocationEntity)
        } else {
            update(locationEntity)
        }
    }

    private fun upsert(conditionEntity: ConditionEntity) {
        val id = insert(conditionEntity)
        if (id == -1L) update(conditionEntity)
    }

    private fun upsertConditions(conditionEntities: List<ConditionEntity>) {
        insertConditions(conditionEntities)
        updateConditions(conditionEntities)
    }

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

    @Query("SELECT count(*) FROM HistoryDayEntity")
    abstract fun getHistoryCount(): Int

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

        upsert(historyEntitiesWrapper.dayConditionEntity)
        upsertConditions(historyEntitiesWrapper.hourConditionEntities)
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
