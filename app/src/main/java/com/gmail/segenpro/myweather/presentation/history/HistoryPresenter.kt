package com.gmail.segenpro.myweather.presentation.history

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class HistoryPresenter : ChildPresenter<HistoryView>() {

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onFirstViewAttach() {
        getHistory()

        /*weatherInteractor.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .subscribe({ Log.d("semLog", javaClass.simpleName + "@" + hashCode() + ", getCurrentLocation(), subscribe(), onSuccess(), thread = ${Thread.currentThread().id}, result = $it") },
                        { Log.e("semLog", javaClass.simpleName + "@" + hashCode() + ", getCurrentLocation(), subscribe(), onError(), thread = ${Thread.currentThread().id}", it) })
                .unsubscribeOnDestroy()*/

        weatherInteractor.getCurrentLocation()//todo потом удалить
                .firstOrError()
                .filter { it is Result.Error }
                .flatMapSingleElement { weatherInteractor.searchLocationsAtServer("Izhevsk") }
                .filter { it is Result.Success }
                .map { (it as Result.Success).data }
                .flatMapObservable { Observable.fromIterable(it) }
                .firstElement()
                .flatMapCompletable { weatherInteractor.setCurrentLocation(it) }
                .subscribeOn(Schedulers.io())
                .subscribe({ Log.d("semLog", javaClass.simpleName + "@" + hashCode() + ", setCurrentLocation(), subscribe(), onSuccess(), thread = ${Thread.currentThread().id}") },
                        { Log.e("semLog", javaClass.simpleName + "@" + hashCode() + ", setCurrentLocation(), subscribe(), onError(), thread = ${Thread.currentThread().id}", it) })
                .unsubscribeOnDestroy()
    }

    private fun getHistory() {
        showProgress(true)
        weatherInteractor.getHistory()
                .map { result ->
                    if (result is Result.Error) {
                        result.copy(isShown = true)
                    } else {
                        result
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    showProgress(false)
                }
                .subscribe({ history ->
                    when (history) {
                        is Result.Success -> viewState.updateState(history.data)
                        is Result.Error -> onError(history.weatherException, history.isShown)
                    }
                })
                .unsubscribeOnDestroy()
    }
}
