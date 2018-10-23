package com.gmail.segenpro.simpleweather.data.database.pojo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.gmail.segenpro.simpleweather.data.database.entities.ConditionEntity
import com.gmail.segenpro.simpleweather.data.database.entities.HourEntity

class HourObject {

    @Embedded
    lateinit var hourEntity: HourEntity

    @Relation(parentColumn = "condition_code", entityColumn = "code")
    lateinit var conditionEntities: List<ConditionEntity>

    val conditionEntity
        get() = conditionEntities[0]
}
