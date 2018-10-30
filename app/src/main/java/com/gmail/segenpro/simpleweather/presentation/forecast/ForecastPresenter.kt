package com.gmail.segenpro.simpleweather.presentation.forecast

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.data.network.Result
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
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val FORECAST_INTERVAL_IN_MINUTES = 10L

@InjectViewState
class ForecastPresenter @Inject constructor(context: Context,
                                            appSectionInteractor: AppSectionInteractor,
                                            reloadContentInteractor: ReloadContentInteractor,
                                            private val locationInteractor: LocationInteractor,
                                            private val weatherInteractor: WeatherInteractor) :
        BaseContentPresenter<ForecastView>(context, appSectionInteractor, reloadContentInteractor) {

    private var forecastTimerDisposable: Disposable = Disposables.empty()

    override fun onFirstViewAttach() {
        getLocation()
    }

    private fun getLocation() {
        locationInteractor.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach {
                    when {
                        it.isOnNext -> {
                            showProgress(true)
                            hideError()
                        }
                        else -> showProgress(false)
                    }
                }
                .subscribe({
                    when (it) {
                        is Result.Success -> startForecastTimer(it.data.name)
                        is Result.Error -> onError(it.weatherException, true)
                    }
                }, { onUnexpectedError(it)
                })
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
                    getForecastOnce(locationName, it == 0L)
                }, { onUnexpectedError(it)
                })
                .unsubscribeOnDestroy()
    }

    private fun getForecastOnce(locationName: String, isFirstResult: Boolean) =
            weatherInteractor.getForecast(locationName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnEvent { _, _ -> showProgress(false) }
                    .subscribe({ onGetForecastResult(it, isFirstResult) }, { onUnexpectedError(it)
                    })
                    .unsubscribeOnDestroy()

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
