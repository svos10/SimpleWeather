package com.gmail.segenpro.simpleweather.data.database.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.entities.ConditionEntity
import com.gmail.segenpro.simpleweather.domain.core.models.Condition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToConditionEntityMapper @Inject constructor() : Mapper<Condition, ConditionEntity> {

    override fun map(from: Condition): ConditionEntity =
            ConditionEntity(from.code,
                    from.text,
                    from.icon)
}
