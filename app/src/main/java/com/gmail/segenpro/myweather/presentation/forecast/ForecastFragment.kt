package com.gmail.segenpro.myweather.presentation.forecast

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildFragment

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

    override fun getLayoutResId(): Int {
        return R.layout.fragment_forecast
    }

    override fun updateState(forecast: Forecast) {
        Log.d("semLog", javaClass.simpleName + "@" + hashCode() + ", updateState(), forecast = $forecast")
        temperature.text = forecast.temperatureInCelsius.toString()
        description.text = forecast.description
        wind.text = forecast.windInKph.toString()
        // todo load icon
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }
}