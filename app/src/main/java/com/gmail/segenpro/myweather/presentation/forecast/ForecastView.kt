package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface ForecastView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateState(forecast: Forecast)
}
