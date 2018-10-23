package com.gmail.segenpro.simpleweather.data.database.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.pojo.HistoryObject
import com.gmail.segenpro.simpleweather.domain.core.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryObjectToModelMapper @Inject constructor() : Mapper<HistoryObject, HistoryDay> {

    override fun map(from: HistoryObject): HistoryDay {
        val astro = Astro(from.astroEntity.sunrise,
                from.astroEntity.sunset,
                from.astroEntity.moonrise,
                from.astroEntity.moonset,
                from.astroEntity.moonPhase,
                from.astroEntity.moonIllumination)

        val location = Location(from.locationEntity.id,
                from.locationEntity.name,
                from.locationEntity.region,
                from.locationEntity.country,
                from.locationEntity.latitudeInDegrees,
                from.locationEntity.longitudeInDegrees,
                from.locationEntity.timeZone)

        val condition = Condition(from.conditionEntity.code,
                from.conditionEntity.text,
                from.conditionEntity.icon)

        val hours = ArrayList<Hour>().apply {
            from.hourObjects.forEach {
                val hourCondition = Condition(it.conditionEntity.code,
                        it.conditionEntity.text,
                        it.conditionEntity.icon)
                val hour = Hour(
                        it.hourEntity.timeEpoch,
                        it.hourEntity.time,
                        it.hourEntity.tempInCelsius,
                        it.hourEntity.tempInFahrenheit,
                        it.hourEntity.maxWindInMph,
                        it.hourEntity.maxWindInMetersPerSecond,
                        it.hourEntity.windDirectionInDegrees,
                        it.hourEntity.windDirection,
                        it.hourEntity.pressureInMb,
                        it.hourEntity.pressureInInches,
                        it.hourEntity.precipitationInMillimeters,
                        it.hourEntity.precipitationInInches,
                        it.hourEntity.humidityInPercentage,
                        it.hourEntity.cloudInPercentage,
                        it.hourEntity.feelsLikeTempInCelsius,
                        it.hourEntity.feelsLikeTempInFahrenheit,
                        it.hourEntity.windchillTempInCelsius,
                        it.hourEntity.windchillTempInFahrenheit,
                        it.hourEntity.heatIndexInCelsius,
                        it.hourEntity.heatIndexInFahrenheit,
                        it.hourEntity.dewPointInCelsius,
                        it.hourEntity.dewPointInFahrenheit,
                        it.hourEntity.willItRain,
                        it.hourEntity.willItSnow,
                        it.hourEntity.isDay,
                        it.hourEntity.visibilityInKm,
                        it.hourEntity.visibilityInMiles,
                        hourCondition)
                this.add(hour)
            }
        }

        return HistoryDay(
                from.historyDayEntity.responseLocaltimeEpoch,
                from.historyDayEntity.responseLocaltime,
                from.historyDayEntity.dateEpoch,
                from.historyDayEntity.date,
                location,
                condition,
                astro,
                hours,
                from.historyDayEntity.maxTempInCelsius,
                from.historyDayEntity.maxTempInFahrenheit,
                from.historyDayEntity.minTempInCelsius,
                from.historyDayEntity.minTempInFahrenheit,
                from.historyDayEntity.avgTempInCelsius,
                from.historyDayEntity.avgTempInFahrenheit,
                from.historyDayEntity.maxWindInMph,
                from.historyDayEntity.maxWindInMetersPerSecond,
                from.historyDayEntity.totalPrecipitationInMillimeters,
                from.historyDayEntity.totalPrecipitationInInches,
                from.historyDayEntity.avgVisibilityInKm,
                from.historyDayEntity.avgVisibilityInMiles,
                from.historyDayEntity.avgHumidityInPercentage,
                from.historyDayEntity.ultravioletIndex)
    }
}
