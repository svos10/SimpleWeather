package com.gmail.segenpro.simpleweather.domain

import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.core.models.Forecast
import io.reactivex.Single

interface ForecastRepository {

    fun getForecast(locationName: String, daysCount: Int): Single<Result<Forecast>>
}
