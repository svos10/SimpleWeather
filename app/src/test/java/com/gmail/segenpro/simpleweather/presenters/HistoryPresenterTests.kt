package com.gmail.segenpro.simpleweather.presenters

import android.content.Context
import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.history.HistoryPresenter
import com.gmail.segenpro.simpleweather.presentation.history.HistoryView
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryPresenterTests : BaseTestsClass() {

    companion object {
        val LOCATION = Location()
        val APP_SECTION = AppSection.HISTORY
        val HISTORY_DAY_LIST = arrayListOf(HistoryDay())
        val WEATHER_EXCEPTION = WeatherException(ErrorType.SERVER_ERROR)
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var appSectionInteractor: AppSectionInteractor

    @Mock
    lateinit var reloadContentInteractor: ReloadContentInteractor

    @Mock
    lateinit var locationInteractor: LocationInteractor

    @Mock
    lateinit var weatherInteractor: WeatherInteractor

    @Mock
    lateinit var historyView: HistoryView

    private lateinit var historyPresenter: HistoryPresenter

    @Before
    fun setup() {
        historyPresenter = HistoryPresenter(context, appSectionInteractor, reloadContentInteractor,
                locationInteractor, weatherInteractor)

        doNothing().`when`(historyView).hideError()
        doNothing().`when`(historyView).showProgress(anyBoolean())
    }

    @Test
    fun updateHistoryIfGetLocation() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.never())
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.never())
        `when`(weatherInteractor.getHistory(any())).thenReturn(Single.just(HISTORY_DAY_LIST.asResult()))

        historyPresenter.attachView(historyView)

        verify(historyView).updateState(HISTORY_DAY_LIST)
    }

    @Test
    fun updateHistoryIfReloadRequested() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.just(APP_SECTION))
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.just(APP_SECTION))
        `when`(weatherInteractor.getHistory(any())).thenReturn(Single.just(HISTORY_DAY_LIST.asResult()))

        historyPresenter.attachView(historyView)

        verify(historyView, times(2)).updateState(HISTORY_DAY_LIST)
    }

    @Test
    fun showErrorIfGetLocationError() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(WEATHER_EXCEPTION.asErrorResult()))

        historyPresenter.attachView(historyView)

        verify(historyView).showError(WEATHER_EXCEPTION)
    }

    @Test
    fun showErrorIfGetHistoryError() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.never())
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.never())
        `when`(weatherInteractor.getHistory(any())).thenReturn(Single.just(WEATHER_EXCEPTION.asErrorResult()))

        historyPresenter.attachView(historyView)

        verify(historyView).showError(WEATHER_EXCEPTION)
    }
}
