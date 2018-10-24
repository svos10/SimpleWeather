package com.gmail.segenpro.simpleweather.presentation.forecast

import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gmail.segenpro.simpleweather.GlideApp
import com.gmail.segenpro.simpleweather.R
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentFragment
import com.gmail.segenpro.simpleweather.presentation.utils.getFullUrlFromProtocolRelative
import javax.inject.Inject
import javax.inject.Provider

class ForecastFragment : BaseContentFragment(), ForecastView {

    @BindView(R.id.info)
    lateinit var info: View

    @BindView(R.id.temperature)
    lateinit var temperature: TextView

    @BindView(R.id.icon)
    lateinit var icon: ImageView

    @BindView(R.id.description)
    lateinit var description: TextView

    @BindView(R.id.wind)
    lateinit var wind: TextView

    @Inject
    lateinit var presenterProvider: Provider<ForecastPresenter>

    @InjectPresenter
    lateinit var presenter: ForecastPresenter

    @ProvidePresenter
    fun providePresenter(): ForecastPresenter = presenterProvider.get()

    override fun inject(component: AppComponent) {
        component.inject(this)
    }

    override fun getLayoutResId() = R.layout.fragment_forecast

    @OnClick(R.id.try_again)
    fun onTryAgainClick() = presenter.onTryAgainClicked()

    override fun updateState(forecast: Forecast) {
        info.visibility = VISIBLE
        temperature.text = getString(R.string.temperature, forecast.temperatureInCelsius.toString())
        wind.text = getString(R.string.wind, forecast.windInMetersPerSecond.toString())
        description.text = forecast.description
        GlideApp.with(this)
                .load(getFullUrlFromProtocolRelative(forecast.icon))
                .into(icon)
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }
}
