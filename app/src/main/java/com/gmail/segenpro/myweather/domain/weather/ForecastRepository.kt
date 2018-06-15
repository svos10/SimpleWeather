package com.gmail.segenpro.myweather.domain.weather

import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import io.reactivex.Single

interface ForecastRepository : BaseRepository {

    fun getForecast(locationName: String, daysCount: Int): Single<Result<Forecast>>
}
