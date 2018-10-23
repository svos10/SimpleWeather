package com.gmail.segenpro.myweather.presentation.history

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.presentation.core.basecontentfragment.BaseContentPresenter
import com.gmail.segenpro.myweather.showError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class HistoryPresenter : BaseContentPresenter<HistoryView>() {

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

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
