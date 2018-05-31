package com.gmail.segenpro.myweather.data.repositories.main

import com.gmail.segenpro.myweather.data.repositories.core.MemoryRepository
import com.gmail.segenpro.myweather.domain.AppSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSectionDataRepository @Inject constructor() : MemoryRepository<AppSection>() {

    override fun getDefault() = AppSection.FORECAST
}