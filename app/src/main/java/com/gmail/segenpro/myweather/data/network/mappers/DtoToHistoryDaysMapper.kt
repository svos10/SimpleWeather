package com.gmail.segenpro.myweather.data.network.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.kilometersPerHourToMetersPerSecond
import com.gmail.segenpro.myweather.data.network.dto.*
import com.gmail.segenpro.myweather.data.roundingToOneDecimalPlace
import com.gmail.segenpro.myweather.domain.core.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToHistoryDaysMapper @Inject constructor(private val locationMapper: Mapper<LocationDto, Location>,
                                                 private val conditionMapper: Mapper<ConditionDto, Condition>,
                                                 private val astroMapper: Mapper<AstroDto, Astro>,
                                                 private val hourMapper: Mapper<HourDto, Hour>) :
        Mapper<HistoryResponseDto, HistoryDays> {

    override fun map(from: HistoryResponseDto): HistoryDays {
        // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
        val historyList = ArrayList<HistoryDay>()
        val location = locationMapper.map(from.locationDto!!)
        from.historyDaysDto!!.historyDays!!.forEach { historyDayDetailsDto ->
            val condition = conditionMapper.map(historyDayDetailsDto.day!!.conditionDto!!)
            val astro = astroMapper.map(historyDayDetailsDto.astro!!)
            val hours = ArrayList<Hour>().apply {
                historyDayDetailsDto.hours!!.forEach { hourDto ->
                    this.add(hourMapper.map(hourDto))
                }
            }
            val history = HistoryDay(from.locationDto.localtimeEpoch!!,
                    from.locationDto.localtime!!,
                    historyDayDetailsDto.dateEpoch!!,
                    historyDayDetailsDto.date!!,
                    location,
                    condition,
                    astro,
                    hours,
                    historyDayDetailsDto.day.maxTemperatureInCelsius!!,
                    historyDayDetailsDto.day.maxTemperatureInFahrenheit!!,
                    historyDayDetailsDto.day.minTemperatureInCelsius!!,
                    historyDayDetailsDto.day.minTemperatureInFahrenheit!!,
                    historyDayDetailsDto.day.avgTemperatureInCelsius!!,
                    historyDayDetailsDto.day.avgTemperatureInFahrenheit!!,
                    historyDayDetailsDto.day.maxWindInMph!!,
                    historyDayDetailsDto.day.maxWindInKph!!.kilometersPerHourToMetersPerSecond().roundingToOneDecimalPlace(),
                    historyDayDetailsDto.day.totalPrecipitationInMillimeters!!,
                    historyDayDetailsDto.day.totalPrecipitationInInches!!,
                    historyDayDetailsDto.day.avgVisibilityInKm!!,
                    historyDayDetailsDto.day.avgVisibilityInMiles!!,
                    historyDayDetailsDto.day.avgHumidityInPercentage!!,
                    historyDayDetailsDto.day.ultravioletIndex!!)
            historyList.add(history)
        }
        return HistoryDays(historyList)
    }
}
