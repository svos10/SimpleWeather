package com.gmail.segenpro.simpleweather.data.network.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.kilometersPerHourToMetersPerSecond
import com.gmail.segenpro.simpleweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.simpleweather.data.roundingToOneDecimalPlace
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToForecastMapper @Inject constructor() : Mapper<ForecastResponseDto, Forecast> {

    override fun map(from: ForecastResponseDto): Forecast =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            Forecast(from.currentDto!!.temperatureInCelsius!!,
                    from.currentDto.conditionDto!!.icon!!,
                    from.currentDto.conditionDto.text!!,
                    from.currentDto.windInKph!!.kilometersPerHourToMetersPerSecond().roundingToOneDecimalPlace())
}
