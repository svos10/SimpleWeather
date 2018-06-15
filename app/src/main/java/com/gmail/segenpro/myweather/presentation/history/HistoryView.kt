package com.gmail.segenpro.myweather.presentation.history

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildView


interface HistoryView : ChildView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateState(historyDays: List<HistoryDay>)
}
