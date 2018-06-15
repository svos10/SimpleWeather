package com.gmail.segenpro.myweather.data.network.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.kilometersPerHourToMetersPerSecond
import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.myweather.data.roundingToOneDecimalPlace
import com.gmail.segenpro.myweather.domain.core.models.Forecast
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
