package com.gmail.segenpro.simpleweather.presentation.forecast

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentView

interface ForecastView : BaseContentView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateState(forecast: Forecast)
}
