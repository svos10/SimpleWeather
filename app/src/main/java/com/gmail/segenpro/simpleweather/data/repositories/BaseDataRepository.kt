package com.gmail.segenpro.simpleweather.data.repositories

import android.content.Context
import com.gmail.segenpro.simpleweather.data.database.WeatherHistoryDao
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.google.gson.Gson
import javax.inject.Inject

abstract class BaseDataRepository {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var weatherService: WeatherService

    @Inject
    lateinit var weatherHistoryDao: WeatherHistoryDao
}
