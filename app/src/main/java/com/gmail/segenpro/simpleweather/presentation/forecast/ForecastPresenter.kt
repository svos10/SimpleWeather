package com.gmail.segenpro.simpleweather.presentation.forecast

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
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

    private var forecastTimerDisposable: Disposable = Disposables.empty()
    private var forecastDisposable: Disposable = Disposables.empty()

    override fun onFirstViewAttach() {
        getLocation()
    }

    private fun getLocation() {
        showProgress(true)
        hideError()
        locationInteractor.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { showProgress(false) }
                .subscribe({
                    when (it) {
                        is Result.Success -> startForecastTimer(it.data.name)
                        is Result.Error -> onError(it.weatherException, true)
                    }
                }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    private fun startForecastTimer(locationName: String) {
        forecastTimerDisposable.dispose()

        forecastTimerDisposable = Observable.merge(
                Observable.interval(0, FORECAST_INTERVAL_IN_MINUTES, TimeUnit.MINUTES),
                observeReloadContentRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { showProgress(false) }
                .subscribe({
                    getForecast(locationName, it == 0L)
                }, { onError(it) })
    }

    private fun observeReloadContentRequest(): Observable<Long> =
            reloadContentInteractor.observeReloadContentRequest()
                    .withLatestFrom(appSectionInteractor.observeAppSection())
                    { reloadedSection: AppSection, currentSection: AppSection ->
                        reloadedSection == currentSection
                    }
                    .filter { it }
                    .map { -1L }

    private fun getForecast(locationName: String, isFirstResult: Boolean) {
        forecastDisposable.dispose()

        forecastDisposable = weatherInteractor.getForecast(locationName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ -> showProgress(false) }
                .subscribe({ onGetForecastResult(it, isFirstResult) }, { onError(it) })
    }

    private fun onGetForecastResult(result: Result<Forecast>, isFirstResult: Boolean) {
        when (result) {
            is Result.Success -> {
                hideError()
                viewState.updateState(result.data)
            }
            is Result.Error -> onError(result.weatherException, isFirstResult)
        }
    }
}
