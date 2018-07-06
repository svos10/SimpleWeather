package com.gmail.segenpro.myweather.presentation.core.childfragment

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface ChildView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(weatherException: WeatherException)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(isShown: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showContent(isShown: Boolean)
}
