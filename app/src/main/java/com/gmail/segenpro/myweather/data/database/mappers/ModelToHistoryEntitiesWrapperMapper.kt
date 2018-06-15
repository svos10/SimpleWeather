package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.*
import com.gmail.segenpro.myweather.data.database.pojo.HistoryEntitiesWrapper
import com.gmail.segenpro.myweather.domain.core.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToHistoryEntitiesWrapperMapper @Inject constructor(private val astroMapper: Mapper<Astro, AstroEntity>,
                                                              private val conditionMapper: Mapper<Condition, ConditionEntity>,
                                                              private val locationMapper: Mapper<Location, LocationEntity>,
                                                              private val hourMapper: Mapper<Hour, HourEntity>,
                                                              private val historyDayMapper: Mapper<HistoryDay, HistoryDayEntity>) :
        Mapper<HistoryDay, HistoryEntitiesWrapper> {

    override fun map(from: HistoryDay): HistoryEntitiesWrapper {
        val locationEntity = locationMapper.map(from.location)
        val dayConditionEntity = conditionMapper.map(from.condition)
        val astroEntity = astroMapper.map(from.astro)
        val hourEntities = hourMapper.map(from.hours)
        val hourConditions = ArrayList<Condition>().apply {
            from.hours.forEach { this.add(it.condition) }
        }
        val hourConditionEntities = conditionMapper.map(hourConditions)
        val historyDayEntity = historyDayMapper.map(from).apply {
            this.copy(responseLocaltimeEpoch = from.location.localtimeEpoch,
                    responseLocaltime = from.location.localtime)
        }
        return HistoryEntitiesWrapper(historyDayEntity,
                dayConditionEntity,
                astroEntity,
                locationEntity,
                hourEntities,
                hourConditionEntities)
    }
}
