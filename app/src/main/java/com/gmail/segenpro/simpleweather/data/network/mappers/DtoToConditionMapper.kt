package com.gmail.segenpro.simpleweather.data.network.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.network.dto.ConditionDto
import com.gmail.segenpro.simpleweather.domain.core.models.Condition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToConditionMapper @Inject constructor() : Mapper<ConditionDto, Condition> {

    override fun map(from: ConditionDto): Condition =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            Condition(from.code!!,
                    from.text!!,
                    from.icon!!)
}
