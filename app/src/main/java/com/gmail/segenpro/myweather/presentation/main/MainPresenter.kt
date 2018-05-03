package com.gmail.segenpro.myweather.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.MainInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    @Inject
    lateinit var interactor: MainInteractor

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        interactor.observeAppSection()
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
        interactor.setAppSection(appSection)
                .subscribe({}, this::onError)
    }
}
