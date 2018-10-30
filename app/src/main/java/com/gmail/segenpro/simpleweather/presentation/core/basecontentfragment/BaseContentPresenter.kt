package com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment

import android.content.Context
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.simpleweather.presentation.core.BasePresenter
import com.gmail.segenpro.simpleweather.presentation.utils.isNetworkAvailable
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

abstract class BaseContentPresenter<View : BaseContentView>(private val context: Context,
                                                            private val appSectionInteractor: AppSectionInteractor,
                                                            private val reloadContentInteractor: ReloadContentInteractor) :
        BasePresenter<View>() {

    protected fun observeReloadContentRequest(): Observable<Long> =
            reloadContentInteractor.observeReloadContentRequest()
                    .withLatestFrom(appSectionInteractor.observeAppSection())
                    { reloadedSection: AppSection, currentSection: AppSection ->
                        reloadedSection == currentSection
                    }
                    .filter { it }
                    .map { -1L }

    protected fun showProgress(isShown: Boolean) = viewState.showProgress(isShown)

    protected fun onError(weatherException: WeatherException, isShown: Boolean) {
        if (isShown) {
            viewState.showError(weatherException)
        }
    }

    protected fun hideError() = viewState.hideError()

    fun onTryAgainClicked() {
        if (!isNetworkAvailable(context)) return

        appSectionInteractor.observeAppSectionOnce()
                .flatMapCompletable { reloadContentInteractor.requestReloadContent(it).ignoreElement() }
                .subscribe({}, { onUnexpectedError(it) })
                .unsubscribeOnDestroy()
    }
}
