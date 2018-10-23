package com.gmail.segenpro.simpleweather.data.network.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.kilometersPerHourToMetersPerSecond
import com.gmail.segenpro.simpleweather.data.network.dto.ConditionDto
import com.gmail.segenpro.simpleweather.data.network.dto.HourDto
import com.gmail.segenpro.simpleweather.data.roundingToOneDecimalPlace
import com.gmail.segenpro.simpleweather.data.toBoolean
import com.gmail.segenpro.simpleweather.domain.core.models.Condition
import com.gmail.segenpro.simpleweather.domain.core.models.Hour
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToHourMapper @Inject constructor(private val conditionMapper: Mapper<ConditionDto, Condition>) : Mapper<HourDto, Hour> {

    override fun map(from: HourDto): Hour =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            Hour(from.timeEpoch!!,
                    from.time!!,
                    from.tempInCelsius!!,
                    from.tempInFahrenheit!!,
                    from.maxWindInMph!!,
                    from.maxWindInKph!!.kilometersPerHourToMetersPerSecond().roundingToOneDecimalPlace(),
                    from.windDirectionInDegrees!!,
                    from.windDirection!!,
                    from.pressureInMb!!,
                    from.pressureInInches!!,
                    from.precipitationInMillimeters!!,
                    from.precipitationInInches!!,
                    from.humidityInPercentage!!,
                    from.cloudInPercentage!!,
                    from.feelsLikeTempInCelsius!!,
                    from.feelsLikeTempInFahrenheit!!,
                    from.windchillTempInCelsius!!,
                    from.windchillTempInFahrenheit!!,
                    from.heatIndexInCelsius!!,
                    from.heatIndexInFahrenheit!!,
                    from.dewPointInCelsius!!,
                    from.dewPointInFahrenheit!!,
                    from.willItRain!!.toBoolean(),
                    from.willItSnow!!.toBoolean(),
                    from.isDay!!.toBoolean(),
                    from.visibilityInKm!!,
                    from.visibilityInMiles!!,
                    conditionMapper.map(from.conditionDto!!))
}
