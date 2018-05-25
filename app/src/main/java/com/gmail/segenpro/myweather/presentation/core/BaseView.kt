package com.gmail.segenpro.myweather.presentation.core

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(throwable: Throwable)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(isShown: Boolean)
}
