package com.gmail.segenpro.simpleweather.presentation.history

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentPresenter
import com.gmail.segenpro.simpleweather.showError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class HistoryPresenter @Inject constructor(context: Context,
                                           appSectionInteractor: AppSectionInteractor,
                                           reloadContentInteractor: ReloadContentInteractor,
                                           private val locationInteractor: LocationInteractor,
                                           private val weatherInteractor: WeatherInteractor) :
        BaseContentPresenter<HistoryView>(context, appSectionInteractor, reloadContentInteractor) {

    override fun onFirstViewAttach() {
        getHistory()
    }

    private fun getHistory() {
        locationInteractor.getCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    hideError()
                    showProgress(true)
                }
                .observeOn(Schedulers.io())
                .flatMapSingle {
                    when (it) {
                        is Result.Success -> getHistoryOnce(it.data)
                        is Result.Error -> Single.just(it.weatherException.asErrorResult<List<HistoryDay>>().showError())
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { showProgress(false) }
                .subscribe({ onGetHistoryResult(it) }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    private fun getHistoryOnce(location: Location): Single<Result<List<HistoryDay>>> =
            weatherInteractor.getHistory(location)
                    .map { result ->
                        if (result is Result.Error) {
                            result.showError()
                        } else {
                            result
                        }
                    }

    private fun onGetHistoryResult(result: Result<List<HistoryDay>>) {
        when (result) {
            is Result.Success -> {
                hideError()
                viewState.updateState(result.data)
            }
            is Result.Error -> onError(result.weatherException, result.isShown)
        }
    }
}
