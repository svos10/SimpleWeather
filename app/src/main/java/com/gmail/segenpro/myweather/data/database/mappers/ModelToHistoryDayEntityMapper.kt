package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.HistoryDayEntity
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToHistoryDayEntityMapper @Inject constructor() : Mapper<HistoryDay, HistoryDayEntity> {

    override fun map(from: HistoryDay): HistoryDayEntity =
            HistoryDayEntity(0,
                    from.condition.code,
                    0,
                    from.dateEpoch,
                    from.date,
                    from.maxTemperatureInCelsius,
                    from.maxTemperatureInFahrenheit,
                    from.minTemperatureInCelsius,
                    from.minTemperatureInFahrenheit,
                    from.avgTemperatureInCelsius,
                    from.avgTemperatureInFahrenheit,
                    from.maxWindInMph,
                    from.maxWindInMetersPerSecond,
                    from.totalPrecipitationInMillimeters,
                    from.totalPrecipitationInInches,
                    from.avgVisibilityInKm,
                    from.avgVisibilityInMiles,
                    from.avgHumidityInPercentage,
                    from.ultravioletIndex,
                    from.responseLocaltimeEpoch,
                    from.responseLocaltime)
}
