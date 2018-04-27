package com.gmail.segenpro.myweather.presentation.charts

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.presentation.core.BasePresenter

@InjectViewState
class ChartsPresenter : BasePresenter<ChartsView>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        //todo логика
    }
}