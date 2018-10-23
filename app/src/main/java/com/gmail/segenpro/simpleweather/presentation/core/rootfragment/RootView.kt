package com.gmail.segenpro.simpleweather.presentation.core.rootfragment

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.presentation.core.BaseView

interface RootView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLocationName(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectAppSection(appSection: AppSection)
}
