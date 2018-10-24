package com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment

import android.content.Context
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.presentation.core.BasePresenter
import com.gmail.segenpro.simpleweather.presentation.utils.isNetworkAvailable

abstract class BaseContentPresenter<View : BaseContentView>(private val context: Context,
                                                            private val appSectionInteractor: AppSectionInteractor,
                                                            private val reloadContentInteractor: ReloadContentInteractor) :
        BasePresenter<View>() {

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
