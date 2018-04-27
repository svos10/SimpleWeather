package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.presentation.core.BasePresenter

@InjectViewState
class ForecastPresenter : BasePresenter<ForecastView>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        //todo подписаться на смену страницы, открыть forecast
    }
}