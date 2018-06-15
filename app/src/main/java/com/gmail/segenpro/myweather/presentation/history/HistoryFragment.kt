package com.gmail.segenpro.myweather.presentation.history

import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.GlideApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildFragment
import com.gmail.segenpro.myweather.presentation.utils.getFullUrlFromProtocolRelative

class HistoryFragment : ChildFragment(), HistoryView {

    @BindView(R.id.yesterday_temperature)
    lateinit var temperature: TextView

    @BindView(R.id.yesterday_icon)
    lateinit var icon: ImageView

    @BindView(R.id.yesterday_description)
    lateinit var description: TextView

    @BindView(R.id.yesterday_wind)
    lateinit var wind: TextView

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    override fun getLayoutResId() = R.layout.fragment_history

    override fun updateState(historyDays: List<HistoryDay>) {//todo написать полный код
        if (historyDays.isEmpty()) return
        temperature.text = getString(R.string.temperature, historyDays[0].maxTemperatureInCelsius.toString())
        wind.text = getString(R.string.wind, historyDays[0].maxWindInMetersPerSecond.toString())
        description.text = historyDays[0].condition.text
        GlideApp.with(this)
                .load(getFullUrlFromProtocolRelative(historyDays[0].condition.icon))
                .into(icon)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}
