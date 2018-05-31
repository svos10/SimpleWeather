package com.gmail.segenpro.myweather.data.network.mappers

import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import kotlin.math.round

private fun Float.kilometersPerHourToMetersPerSecond() = this * 1000 / 3600

private fun Float.roundingToOneDecimalPlace() = round(this * 10) / 10

class ToForecastMapper : Mapper<ForecastResponseDto, Forecast> {

    override fun map(from: ForecastResponseDto): Forecast =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            Forecast(from.currentDto!!.temperatureInCelsius!!,
                    from.currentDto.conditionDto!!.icon!!,
                    from.currentDto.conditionDto.text!!,
                    from.currentDto.windInKph!!.kilometersPerHourToMetersPerSecond().roundingToOneDecimalPlace())
}