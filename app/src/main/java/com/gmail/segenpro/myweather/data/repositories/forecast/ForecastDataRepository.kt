package com.gmail.segenpro.myweather.data.repositories.forecast

import com.gmail.segenpro.myweather.data.repositories.core.MemoryRepository
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastDataRepository @Inject constructor() : MemoryRepository<AppSection>(), MainRepository {
    //todo code
    override fun getDefault() = AppSection.FORECAST
}