package com.gmail.segenpro.simpleweather.data.database.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.entities.HourEntity
import com.gmail.segenpro.simpleweather.domain.core.models.Hour
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToHourEntityMapper @Inject constructor() : Mapper<Hour, HourEntity> {

    override fun map(from: Hour): HourEntity =
            HourEntity(0,
                    0,
                    from.condition.code,
                    from.timeEpoch,
                    from.time,
                    from.tempInCelsius,
                    from.tempInFahrenheit,
                    from.maxWindInMph,
                    from.maxWindInMetersPerSecond,
                    from.windDirectionInDegrees,
                    from.windDirection,
                    from.pressureInMb,
                    from.pressureInInches,
                    from.precipitationInMillimeters,
                    from.precipitationInInches,
                    from.humidityInPercentage,
                    from.cloudInPercentage,
                    from.feelsLikeTempInCelsius,
                    from.feelsLikeTempInFahrenheit,
                    from.windchillTempInCelsius,
                    from.windchillTempInFahrenheit,
                    from.heatIndexInCelsius,
                    from.heatIndexInFahrenheit,
                    from.dewPointInCelsius,
                    from.dewPointInFahrenheit,
                    from.willItRain,
                    from.willItSnow,
                    from.isDay,
                    from.visibilityInKm,
                    from.visibilityInMiles)
}
