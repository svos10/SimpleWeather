package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
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
        showProgress(true)
        Observable.merge(Observable.interval(0, FORECAST_INTERVAL_IN_MINUTES, TimeUnit.MINUTES), observeReloadContentRequest())
                .observeOn(Schedulers.io())
                .flatMap { value ->
                    weatherInteractor.getForecast()
                            .map { result ->
                                if (value == 0L && result is Result.Error) {
                                    result.copy(isShown = true)
                                } else {
                                    result
                                }
                            }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach {
                    showProgress(false)
                }
                .subscribe {
                    when (it) {
                        is Result.Success -> viewState.updateState(it.data)
                        is Result.Error -> onError(it.weatherException, it.isShown)
                    }
                }
                .unsubscribeOnDestroy()
    }
}
