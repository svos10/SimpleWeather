package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.showError
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val FORECAST_INTERVAL_IN_MINUTES = 10L

@InjectViewState
class ForecastPresenter : ChildPresenter<ForecastView>() {

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onFirstViewAttach() {
        showContent(false)
        getForecast()
    }

    private fun getForecast() {
        weatherInteractor.getCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    hideError()
                    showProgress(true)
                    if (it is Result.Success) viewState.updateLocation(it.data.name)
                }
                .flatMap {
                    when (it) {
                        is Result.Success -> getForecastPeriodically(it.data.name)
                        is Result.Error -> Observable.just(it.weatherException.asErrorResult<Forecast>().showError())
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { showProgress(false) }
                .subscribe {
                    when (it) {
                        is Result.Success -> {
                            hideError()
                            viewState.updateState(it.data)
                        }
                        is Result.Error -> onError(it.weatherException, it.isShown)
                    }
                    showContent(true)
                }
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
}
