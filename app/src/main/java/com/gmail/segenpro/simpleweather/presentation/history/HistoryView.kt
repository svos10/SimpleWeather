package com.gmail.segenpro.simpleweather.presentation.history

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentView

interface HistoryView : BaseContentView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateState(historyDays: List<HistoryDay>)
}
