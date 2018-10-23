package com.gmail.segenpro.myweather.data.repositories.weather

import com.gmail.segenpro.myweather.asErrorResult
import com.gmail.segenpro.myweather.asResult
import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.data.network.dto.ForecastResponseDto
import com.gmail.segenpro.myweather.data.repositories.BaseDataRepository
import com.gmail.segenpro.myweather.data.retrofitResponseToResult
import com.gmail.segenpro.myweather.domain.core.models.Forecast
import com.gmail.segenpro.myweather.domain.ForecastRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastDataRepository @Inject constructor(private val forecastMapper: Mapper<ForecastResponseDto, Forecast>) :
        BaseDataRepository(), ForecastRepository {

    override fun getForecast(locationName: String, daysCount: Int): Single<Result<Forecast>> =
            weatherService.getForecast(locationName, daysCount)
                    .retrofitResponseToResult(context, gson)
                    .map {
                        when (it) {
                            is Result.Success -> forecastMapper.map(it.data).asResult()
                            is Result.Error -> it.weatherException.asErrorResult()
                        }
                    }
}
