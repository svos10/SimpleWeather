package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val FORECAST_INTERVAL_IN_MINUTES = 10L
//private const val FORECAST_INTERVAL = 5L

@InjectViewState
class ForecastPresenter : BasePresenter<ForecastView>() {

    @Inject
    lateinit var weatherInteractor: WeatherInteractor

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        showProgress(true)
        Observable.interval(0, FORECAST_INTERVAL_IN_MINUTES, TimeUnit.MINUTES)
        //Observable.interval(0, FORECAST_INTERVAL, TimeUnit.SECONDS/*, Schedulers.io()*/)
                //.doOnNext { value -> Log.d("semLog", javaClass.simpleName + "@" + hashCode() + ", interval(), thread = ${Thread.currentThread().id}, value = $value") }
                .flatMapSingle { weatherInteractor.getForecast() }
                //.flatMapSingle { Single.just(Forecast(0f, "", "", 0f)).delay(2L, TimeUnit.SECONDS) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { showProgress(false) }
                .doOnError { showProgress(false) }
                //.doOnNext { forecast -> Log.d("semLog", javaClass.simpleName + "@" + hashCode() + ", interval(), forecast = $forecast") }
                //.doOnError { e -> Log.e("semLog", javaClass.simpleName + "@" + hashCode() + ", interval(), error",e)}
                .subscribe({ viewState.updateState(it) }, { onError(it) })
                .unsubscribeOnDestroy()

    }
}
