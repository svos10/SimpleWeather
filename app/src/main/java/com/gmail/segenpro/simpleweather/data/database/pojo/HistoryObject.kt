package com.gmail.segenpro.simpleweather.data.database.pojo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.gmail.segenpro.simpleweather.data.database.entities.*

class HistoryObject {

    @Embedded
    lateinit var historyDayEntity: HistoryDayEntity

    @Relation(parentColumn = "condition_code", entityColumn = "code")
    lateinit var conditionEntities: List<ConditionEntity>

    val conditionEntity
        get() = conditionEntities[0]

    @Relation(parentColumn = "location_id", entityColumn = "id")
    lateinit var locationEntities: List<LocationEntity>

    val locationEntity
        get() = locationEntities[0]

    @Relation(parentColumn = "id", entityColumn = "history_id")
    lateinit var astroEntities: List<AstroEntity>

    val astroEntity
        get() = astroEntities[0]

    @Relation(parentColumn = "id", entityColumn = "history_id", entity = HourEntity::class)
    lateinit var hourObjects: List<HourObject>
}
