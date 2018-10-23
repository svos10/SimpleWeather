package com.gmail.segenpro.myweather.presentation.core.basecontentfragment

import android.content.Context
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import com.gmail.segenpro.myweather.presentation.utils.isNetworkAvailable
import javax.inject.Inject

abstract class BaseContentPresenter<View : BaseContentView> : BasePresenter<View>() {

    @Inject
    lateinit var context: Context

    @Inject
    protected lateinit var weatherInteractor: WeatherInteractor

    protected fun showProgress(isShown: Boolean) = viewState.showProgress(isShown)

    protected fun onError(weatherException: WeatherException, isShown: Boolean) {
        super.onError(weatherException)
        if (isShown) {
            viewState.showError(weatherException)
        }
    }

    protected fun hideError() = viewState.hideError()

    fun onTryAgainClicked() {
        if (!isNetworkAvailable(context)) return

        appSectionInteractor.observeAppSectionOnce()
                .flatMapCompletable { reloadContentInteractor.requestReloadContent(it).ignoreElement() }
                .subscribe({}, { onError(it) })
                .unsubscribeOnDestroy()
    }
}
