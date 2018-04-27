package com.gmail.segenpro.myweather.presentation.forecast

import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.BaseFragment

class ForecastFragment : BaseFragment(), ForecastView {

    @InjectPresenter
    lateinit var forecastPresenter: ForecastPresenter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_forecast
    }

    companion object {

        fun newInstance() = ForecastFragment()
    }
}