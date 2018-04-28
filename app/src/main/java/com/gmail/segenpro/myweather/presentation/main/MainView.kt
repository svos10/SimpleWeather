package com.gmail.segenpro.myweather.presentation.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseView

interface MainView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectAppSection(appSection: AppSection)
}
