package com.gmail.segenpro.myweather.presentation.core.navigator

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.forecast.ForecastFragment
import com.gmail.segenpro.myweather.presentation.history.HistoryFragment
import ru.terrakok.cicerone.android.SupportFragmentNavigator

class Navigator(fragmentManager: FragmentManager, @IdRes containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId) {

    var onExitListener: OnExitListener? = null

    override fun createFragment(screenKey: String?, data: Any?): Fragment? = screenKey?.let {
        when (screenKey) {
            AppSection.FORECAST.name -> ForecastFragment.newInstance()
            AppSection.CHARTS.name -> HistoryFragment.newInstance()
            else -> null
        }
    }

    override fun exit() {
        onExitListener?.onExit()
    }

    override fun showSystemMessage(message: String?) {
        Toast.makeText(MyWeatherApp.instance, message, Toast.LENGTH_SHORT).show()
    }

    interface OnExitListener {
        fun onExit()
    }
}