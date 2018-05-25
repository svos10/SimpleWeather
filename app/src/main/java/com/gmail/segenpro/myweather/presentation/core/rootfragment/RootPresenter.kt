package com.gmail.segenpro.myweather.presentation.core.rootfragment

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.MainInteractor
import com.gmail.segenpro.myweather.domain.weather.WeatherInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import javax.inject.Inject

@InjectViewState
class RootPresenter : BasePresenter<RootView>() {

    @Inject
    lateinit var mainInteractor: MainInteractor

    lateinit var weatherInteractor: WeatherInteractor//todo-sem delete

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        mainInteractor.observeAppSection()
                .subscribe({ appSection ->
                    viewState.selectAppSection(appSection)
                    router.replaceScreen(appSection.name)
                }, this::onError)
                .unsubscribeOnDestroy()
    }

    fun openForecast() {
        open(AppSection.FORECAST)
    }

    fun openCharts() {
        open(AppSection.CHARTS)
    }

    private fun open(appSection: AppSection) {
        mainInteractor.setAppSection(appSection)
                .subscribe({}, this::onError)
    }
}
