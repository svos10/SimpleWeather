package com.gmail.segenpro.myweather.presentation.core.childfragment

import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.myweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.myweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

abstract class ChildPresenter<View : ChildView> : BasePresenter<View>() {

    @Inject
    lateinit var appSectionInteractor: AppSectionInteractor

    @Inject
    lateinit var reloadContentInteractor: ReloadContentInteractor

    @Inject
    lateinit var weatherInteractor: WeatherInteractor

    protected fun onError(weatherException: WeatherException, isShownError: Boolean) {
        weatherException.printStackTrace()
        if (isShownError) viewState.showError(weatherException)
    }

    protected fun hideError() = viewState.hideError()

    protected fun showProgress(isShown: Boolean) = viewState.showProgress(isShown)

    protected fun observeReloadContentRequest(): Observable<Long> = reloadContentInteractor.observeReloadContentRequest()
            .withLatestFrom(appSectionInteractor.observeAppSection(),
                    { reloadedSection: AppSection, currentSection: AppSection -> reloadedSection == currentSection })
            .filter { it }
            .map { -1L }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                hideError()
                showProgress(true)
            }
}
