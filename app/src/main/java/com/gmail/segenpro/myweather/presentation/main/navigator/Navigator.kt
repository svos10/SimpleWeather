package com.gmail.segenpro.myweather.presentation.main.navigator

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.history.HistoryFragment
import com.gmail.segenpro.myweather.presentation.forecast.ForecastFragment
import ru.terrakok.cicerone.android.SupportAppNavigator

class Navigator(activity: FragmentActivity) : SupportAppNavigator(activity, CONTENT_CONTAINER_ID) {

    override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = null

    override fun createFragment(screenKey: String?, data: Any?): Fragment? = screenKey?.let {
        when (screenKey) {
            AppSection.FORECAST.name -> ForecastFragment.newInstance()
            AppSection.CHARTS.name -> HistoryFragment.newInstance()
            else -> null
        }
    }

    companion object {
        @IdRes
        private val CONTENT_CONTAINER_ID = R.id.content_container
    }
}