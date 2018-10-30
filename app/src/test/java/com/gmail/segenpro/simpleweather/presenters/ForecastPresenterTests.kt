package com.gmail.segenpro.simpleweather.presenters

import android.content.Context
import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.forecast.FORECAST_INTERVAL_IN_MINUTES
import com.gmail.segenpro.simpleweather.presentation.forecast.ForecastPresenter
import com.gmail.segenpro.simpleweather.presentation.forecast.ForecastView
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
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ForecastPresenterTests : BaseTestsClass() {

    companion object {
        val LOCATION = Location()
        val FORECAST = Forecast()
        val APP_SECTION = AppSection.FORECAST
        val WEATHER_EXCEPTION = WeatherException(ErrorType.SERVER_ERROR)
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(true)

    private val testScheduler by lazy { schedulerRule.testScheduler }

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
    lateinit var forecastView: ForecastView

    private lateinit var forecastPresenter: ForecastPresenter

    @Before
    fun setup() {
        forecastPresenter = ForecastPresenter(context, appSectionInteractor, reloadContentInteractor,
                locationInteractor, weatherInteractor)

        doNothing().`when`(forecastView).hideError()
        doNothing().`when`(forecastView).showProgress(anyBoolean())
    }

    @Test
    fun updateForecastPeriodicallyIfGetLocation() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.never())
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.never())
        `when`(weatherInteractor.getForecast(anyString())).thenReturn(Single.just(FORECAST.asResult()))

        forecastPresenter.attachView(forecastView)
        testScheduler.advanceTimeBy(FORECAST_INTERVAL_IN_MINUTES + 1, TimeUnit.MINUTES)

        verify(forecastView, times(2)).updateState(FORECAST)
    }

    @Test
    fun updateForecastIfReloadRequested() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.just(APP_SECTION))
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.just(APP_SECTION))
        `when`(weatherInteractor.getForecast(anyString())).thenReturn(Single.just(FORECAST.asResult()))

        forecastPresenter.attachView(forecastView)
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(forecastView, times(2)).updateState(FORECAST)
    }

    @Test
    fun showErrorIfGetLocationError() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(WEATHER_EXCEPTION.asErrorResult()))

        forecastPresenter.attachView(forecastView)
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(forecastView).showError(WEATHER_EXCEPTION)
    }

    @Test
    fun showErrorIfGetForecastError() {
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(LOCATION.asResult()))
        `when`(reloadContentInteractor.observeReloadContentRequest()).thenReturn(Observable.never())
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.never())
        `when`(weatherInteractor.getForecast(anyString())).thenReturn(Single.just(WEATHER_EXCEPTION.asErrorResult()))

        forecastPresenter.attachView(forecastView)
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(forecastView).showError(WEATHER_EXCEPTION)
    }
}
