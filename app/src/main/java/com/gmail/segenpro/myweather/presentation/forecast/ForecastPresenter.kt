package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.presentation.core.basecontentfragment.BaseContentPresenter
import com.gmail.segenpro.myweather.showError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val FORECAST_INTERVAL_IN_MINUTES = 10L

@InjectViewState
class ForecastPresenter : BaseContentPresenter<ForecastView>() {

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

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
