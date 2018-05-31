package com.gmail.segenpro.myweather.presentation.forecast

import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.GlideApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildFragment
import com.gmail.segenpro.myweather.presentation.utils.getFullUrlFromProtocolRelative

class ForecastFragment : ChildFragment(), ForecastView {

    @BindView(R.id.temperature)
    lateinit var temperature: TextView

    @BindView(R.id.icon)
    lateinit var icon: ImageView

    @BindView(R.id.description)
    lateinit var description: TextView

    @BindView(R.id.wind)
    lateinit var wind: TextView

    @InjectPresenter
    lateinit var presenter: ForecastPresenter

    override fun getLayoutResId() = R.layout.fragment_forecast

    override fun updateState(forecast: Forecast) {
        temperature.text = getString(R.string.temperature, forecast.temperatureInCelsius.toString())
        wind.text = getString(R.string.wind, forecast.windInMetersPerSecond.toString())
        description.text = forecast.description
        GlideApp.with(this).load(getFullUrlFromProtocolRelative(forecast.icon)).into(icon)
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }
}
