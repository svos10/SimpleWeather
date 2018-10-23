package com.gmail.segenpro.simpleweather.presentation.core.navigator

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.presentation.forecast.ForecastFragment
import com.gmail.segenpro.simpleweather.presentation.history.HistoryFragment
import ru.terrakok.cicerone.android.SupportFragmentNavigator

class Navigator(fragmentManager: FragmentManager, @IdRes containerId: Int) :
        SupportFragmentNavigator(fragmentManager, containerId) {

    var onExitListener: OnExitListener? = null

    override fun createFragment(screenKey: String?, data: Any?): Fragment? = screenKey?.let {
        when (it) {
            AppSection.FORECAST.name -> ForecastFragment.newInstance()
            AppSection.HISTORY.name -> HistoryFragment.newInstance()
            else -> null
        }
    }

    override fun exit() {
        onExitListener?.onExit()
    }

    override fun showSystemMessage(message: String?) =
            Toast.makeText(SimpleWeatherApp.instance, message, Toast.LENGTH_SHORT).show()

    interface OnExitListener {
        fun onExit()
    }
}
