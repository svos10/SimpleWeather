package com.gmail.segenpro.simpleweather.presentation.history

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class HistoryPresenter @Inject constructor(context: Context,
                                           appSectionInteractor: AppSectionInteractor,
                                           reloadContentInteractor: ReloadContentInteractor,
                                           private val locationInteractor: LocationInteractor,
                                           private val weatherInteractor: WeatherInteractor) :
        BaseContentPresenter<HistoryView>(context, appSectionInteractor, reloadContentInteractor) {

    private var historyReloadDisposable: Disposable = Disposables.empty()

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
                        is Result.Success -> {
                            getHistoryOnce(it.data)
                            historyReload(it.data)
                        }
                        is Result.Error -> onError(it.weatherException, true)
                    }
                }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    private fun historyReload(location: Location) {
        historyReloadDisposable.dispose()

        historyReloadDisposable = observeReloadContentRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { showProgress(false) }
                .subscribe({
                    getHistoryOnce(location)
                }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    private fun getHistoryOnce(location: Location) =
            weatherInteractor.getHistory(location)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnEvent { _, _ -> showProgress(false) }
                    .subscribe({ onGetHistoryResult(it) }, { onError(it) })
                    .unsubscribeOnDestroy()

    private fun onGetHistoryResult(result: Result<List<HistoryDay>>) {
        when (result) {
            is Result.Success -> {
                hideError()
                viewState.updateState(result.data)
            }
            is Result.Error -> onError(result.weatherException, true)
        }
    }
}
