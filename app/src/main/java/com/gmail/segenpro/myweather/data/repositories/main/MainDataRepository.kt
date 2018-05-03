package com.gmail.segenpro.myweather.data.repositories.main

import com.gmail.segenpro.myweather.data.repositories.core.MemoryRepository
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDataRepository @Inject constructor() : MemoryRepository<AppSection>(), MainRepository {

    override fun getDefault() = AppSection.FORECAST
}