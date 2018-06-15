package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.ConditionEntity
import com.gmail.segenpro.myweather.domain.core.models.Condition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToConditionEntityMapper @Inject constructor() : Mapper<Condition, ConditionEntity> {

    override fun map(from: Condition): ConditionEntity =
            ConditionEntity(from.code,
                    from.text,
                    from.icon)
}
