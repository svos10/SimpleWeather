package com.gmail.segenpro.myweather.presentation.history

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.domain.core.models.Location
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildPresenter
import com.gmail.segenpro.myweather.showError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class HistoryPresenter : ChildPresenter<HistoryView>() {

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onFirstViewAttach() {
        showContent(false)
        getHistory()
    }

    private fun getHistory() {
        weatherInteractor.getCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    hideError()
                    showProgress(true)
                    if (it is Result.Success) viewState.updateLocation(it.data.name)

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
                .subscribe { history ->
                    when (history) {
                        is Result.Success -> {
                            hideError()
                            viewState.updateState(history.data)
                        }
                        is Result.Error -> onError(history.weatherException, history.isShown)
                    }
                    showContent(true)
                }
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
}
