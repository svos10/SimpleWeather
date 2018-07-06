package com.gmail.segenpro.myweather.presentation.core.rootfragment

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface RootView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectAppSection(appSection: AppSection)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(isShown: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showContent(isShown: Boolean)
}
