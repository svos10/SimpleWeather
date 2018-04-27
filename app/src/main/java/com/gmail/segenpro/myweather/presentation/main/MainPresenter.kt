package com.gmail.segenpro.myweather.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import com.gmail.segenpro.myweather.presentation.main.navigator.FORECAST

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        //todo подписаться на события перехода на экран
        openForecast()
    }

    private fun openForecast() {
        router.navigateTo(FORECAST)
    }
}