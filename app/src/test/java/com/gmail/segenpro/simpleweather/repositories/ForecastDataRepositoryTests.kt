package com.gmail.segenpro.simpleweather.repositories

import android.content.Context
import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.gmail.segenpro.simpleweather.data.network.dto.*
import com.gmail.segenpro.simpleweather.data.repositories.weather.ForecastDataRepository
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastDataRepositoryTests : BaseTestsClass() {

    companion object {
        const val LOCATION_NAME = ""
        const val DAYS_COUNT = 1
        val FORECAST = Forecast()
        private val LOCATION_DTO = LocationDto(0f, 0f, "", "",
                "", "", 0, "")
        private val CONDITION_DTO = ConditionDto("", "", 0)
        private val CURRENT_DTO = CurrentDto("", 0, 0f,
                0f, 0f, 0f,
                CONDITION_DTO, 0f, 0f, 0, "",
                0f, 0f, 0f, 0f,
                0, 0, 0, 0f, 0f)
        private val DAY_DTO = DayDto(0f, 0f, 0f,
                0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0, CONDITION_DTO, 0f)
        private val ASTRO_DTO = AstroDto("", "", "", "", "",
                "")
        private val FORECAST_DAY_DETAILS_DTO = ForecastDayDetailsDto("", 0, DAY_DTO, ASTRO_DTO)
        private val FORECAST_DAYS_DTO = ForecastDaysDto(arrayListOf(FORECAST_DAY_DETAILS_DTO))
        val FORECAST_RESPONSE_DTO = ForecastResponseDto(LOCATION_DTO, CURRENT_DTO, FORECAST_DAYS_DTO)
        private val INCORRECT_LOCATION_DTO = LocationDto(null, 0f, "", "",
                "", "", 0, "")
        val INCORRECT_FORECAST_RESPONSE_DTO = ForecastResponseDto(INCORRECT_LOCATION_DTO, CURRENT_DTO, FORECAST_DAYS_DTO)
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var gson: Gson

    @Mock
    lateinit var weatherService: WeatherService

    @Mock
    lateinit var forecastMapper: Mapper<ForecastResponseDto, Forecast>

    private lateinit var forecastDataRepository: ForecastDataRepository

    @Before
    fun setup() {
        forecastDataRepository = ForecastDataRepository(context, gson, weatherService, forecastMapper)
    }

    @Test
    fun getForecastFromServerIfGetLocationNameAndDaysCount() {
        `when`(weatherService.getForecast(anyString(), anyInt())).thenReturn(Single.just(FORECAST_RESPONSE_DTO))
        `when`(forecastMapper.map(any<ForecastResponseDto>())).thenReturn(FORECAST)

        val observer = forecastDataRepository.getForecast(LOCATION_NAME, DAYS_COUNT).test()

        observer.assertValue { it == FORECAST.asResult() }
    }

    @Test
    fun getExceptionIfIncorrectServerData() {
        `when`(weatherService.getForecast(anyString(), anyInt())).thenReturn(Single.just(INCORRECT_FORECAST_RESPONSE_DTO))
        `when`(context.getString(anyInt())).thenReturn("")

        val observer = forecastDataRepository.getForecast(LOCATION_NAME, DAYS_COUNT).test()

        observer.assertValue { it is Result.Error<Forecast> }
    }
}
