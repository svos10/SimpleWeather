package com.gmail.segenpro.simpleweather.data.database.pojo

import com.gmail.segenpro.simpleweather.data.database.entities.*

data class HistoryEntitiesWrapper(
        val historyDayEntity: HistoryDayEntity,
        val dayConditionEntity: ConditionEntity,
        val astroEntity: AstroEntity,
        val locationEntity: LocationEntity,
        val hourEntities: List<HourEntity>,
        val hourConditionEntities: List<ConditionEntity>
)
