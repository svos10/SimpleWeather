package com.gmail.segenpro.simpleweather.presentation.forecast

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentPresenter
import com.gmail.segenpro.simpleweather.showError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val FORECAST_INTERVAL_IN_MINUTES = 10L

@InjectViewState
class ForecastPresenter @Inject constructor(context: Context,
                                            private val appSectionInteractor: AppSectionInteractor,
                                            private val reloadContentInteractor: ReloadContentInteractor,
                                            private val locationInteractor: LocationInteractor,
                                            private val weatherInteractor: WeatherInteractor) :
        BaseContentPresenter<ForecastView>(context, appSectionInteractor, reloadContentInteractor) {

    override fun onFirstViewAttach() {
        getForecast()
    }

    private fun getForecast() {
        locationInteractor.getCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    hideError()
                    showProgress(true)
                }
                .flatMap {
                    when (it) {
                        is Result.Success -> getForecastPeriodically(it.data.name)
                        is Result.Error -> Observable.just(it.weatherException.asErrorResult<Forecast>().showError())
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { showProgress(false) }
                .subscribe({ onGetForecastResult(it) }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    private fun getForecastPeriodically(locationName: String): Observable<Result<Forecast>> =
            updateRequest()
                    .observeOn(Schedulers.io())
                    .flatMapSingle { value ->
                        weatherInteractor.getForecast(locationName)
                                .map { result ->
                                    if (value == 0L && result is Result.Error) {
                                        result.showError()
                                    } else {
                                        result
                                    }
                                }
                    }

    private fun updateRequest(): Observable<Long> = Observable.merge(
            Observable.interval(0, FORECAST_INTERVAL_IN_MINUTES, TimeUnit.MINUTES),
            observeReloadContentRequest()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        hideError()
                        showProgress(true)
                    }
    )

    private fun observeReloadContentRequest(): Observable<Long> =
            reloadContentInteractor.observeReloadContentRequest()
                    .withLatestFrom(appSectionInteractor.observeAppSection()
                    ) { reloadedSection: AppSection, currentSection: AppSection ->
                        reloadedSection == currentSection
                    }
                    .filter { it }
                    .map { -1L }

    private fun onGetForecastResult(result: Result<Forecast>) {
        when (result) {
            is Result.Success -> {
                hideError()
                viewState.updateState(result.data)
            }
            is Result.Error -> onError(result.weatherException, result.isShown)
        }
    }
}
