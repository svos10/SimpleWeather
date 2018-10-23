package com.gmail.segenpro.myweather.presentation.core.rootfragment

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface RootView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLocationName(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectAppSection(appSection: AppSection)
}
