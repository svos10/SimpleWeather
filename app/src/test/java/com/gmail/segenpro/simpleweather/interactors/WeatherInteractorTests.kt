package com.gmail.segenpro.simpleweather.interactors

import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.ForecastRepository
import com.gmail.segenpro.simpleweather.domain.HistoryRepository
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WeatherInteractorTests : BaseTestsClass() {

    companion object {
        val LOCATION = Location(id = 10)
        val HISTORY_DAY = HistoryDay(location = LOCATION)
        val HISTORY_DAY_LIST = arrayListOf(HISTORY_DAY)
        val WEATHER_EXCEPTION = WeatherException(ErrorType.NETWORK_UNAVAILABLE)
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var forecastRepository: ForecastRepository

    @Mock
    lateinit var historyRepository: HistoryRepository

    private lateinit var weatherInteractor: WeatherInteractor

    @Before
    fun setup() {
        weatherInteractor = WeatherInteractor(forecastRepository, historyRepository)
        `when`(historyRepository.getHistoryFromServer(anyString(), anyString())).thenReturn(Single.just(HISTORY_DAY.asResult()))
        `when`(historyRepository.putHistoryDayToDb(any())).thenReturn(Completable.complete())
        `when`(historyRepository.deleteOldHistoryFromDb(any(), anyInt())).thenReturn(Completable.complete())
    }

    @Test
    fun getHistoryFromDbIfDbHistoryDayListIsNotEmpty() {
        `when`(historyRepository.getHistoryFromDb(any())).thenReturn(Single.just(HISTORY_DAY_LIST))

        val observer = weatherInteractor.getHistory(LOCATION).test()

        observer.assertValue { it == HISTORY_DAY_LIST.asResult() }
    }

    @Test
    fun getExceptionIfDbHistoryDayListIsEmpty() {
        `when`(historyRepository.getHistoryFromDb(any())).thenReturn(Single.just(Collections.emptyList()))

        val observer = weatherInteractor.getHistory(LOCATION).test()

        observer.assertValue { it == WEATHER_EXCEPTION.asErrorResult<List<HistoryDay>>() }
    }

    @Test
    fun getSeveralHistoryDaysFromServerIfGetLocation() {
        `when`(historyRepository.getHistoryFromDb(any())).thenReturn(Single.just(HISTORY_DAY_LIST))

        weatherInteractor.getHistory(LOCATION).test()

        verify(historyRepository, atLeastOnce()).getHistoryFromServer(eq(LOCATION.name), anyString())
    }

    @Test
    fun getSeveralHistoryDaysToDbIfGetLocation() {
        `when`(historyRepository.getHistoryFromDb(any())).thenReturn(Single.just(HISTORY_DAY_LIST))

        weatherInteractor.getHistory(LOCATION).test()

        verify(historyRepository, atLeastOnce()).putHistoryDayToDb(HISTORY_DAY)
    }

    @Test
    fun deleteOldHistoryFromDbIfGetLocation() {
        `when`(historyRepository.getHistoryFromDb(any())).thenReturn(Single.just(HISTORY_DAY_LIST))

        weatherInteractor.getHistory(LOCATION).test()

        verify(historyRepository).deleteOldHistoryFromDb(eq(LOCATION), anyInt())
    }
}
