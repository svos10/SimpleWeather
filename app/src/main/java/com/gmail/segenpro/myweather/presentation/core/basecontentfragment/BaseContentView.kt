package com.gmail.segenpro.myweather.presentation.core.basecontentfragment

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface BaseContentView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(isShown: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(weatherException: WeatherException)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideError()
}
