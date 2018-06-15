package com.gmail.segenpro.myweather.presentation.history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.gmail.segenpro.myweather.GlideApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.presentation.utils.getFullUrlFromProtocolRelative
import java.text.SimpleDateFormat
import java.util.*

private val serverDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
private val localDateFormat by lazy { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

private fun String.formatHistoryDate() = localDateFormat.format(serverDateFormat.parse(this))

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var historyDays: List<HistoryDay> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = historyDays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val historyDayView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(historyDayView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(context, historyDays[position])

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.date)
        lateinit var date: TextView

        @BindView(R.id.temperature)
        lateinit var temperature: TextView

        @BindView(R.id.icon)
        lateinit var icon: ImageView

        @BindView(R.id.description)
        lateinit var description: TextView

        @BindView(R.id.wind)
        lateinit var wind: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(context: Context, historyDay: HistoryDay) {
            date.text = historyDay.date.formatHistoryDate()
            temperature.text = context.getString(R.string.temperature, historyDay.avgTemperatureInCelsius.toString())
            wind.text = context.getString(R.string.wind, historyDay.maxWindInMetersPerSecond.toString())
            description.text = historyDay.condition.text
            GlideApp.with(context)
                    .load(getFullUrlFromProtocolRelative(historyDay.condition.icon))
                    .into(icon)
        }
    }
}
