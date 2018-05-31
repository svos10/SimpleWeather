package com.gmail.segenpro.myweather.presentation.core.childfragment

import com.gmail.segenpro.myweather.data.network.WeatherException
import com.gmail.segenpro.myweather.presentation.core.BaseFragment
import com.gmail.segenpro.myweather.presentation.core.rootfragment.RootFragment

abstract class ChildFragment : BaseFragment(), ChildView {

    final override fun showError(weatherException: WeatherException) {
        (parentFragment as? RootFragment)?.showError(weatherException)
    }

    final override fun hideError() {
        (parentFragment as? RootFragment)?.hideError()
    }

    final override fun showProgress(isShown: Boolean) {
        (parentFragment as? RootFragment)?.showProgress(isShown)
    }
}
