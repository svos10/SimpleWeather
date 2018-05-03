package com.gmail.segenpro.myweather.domain.main

import com.gmail.segenpro.myweather.data.network.WeatherService
import com.gmail.segenpro.myweather.data.repositories.main.MainDataRepository
import com.gmail.segenpro.myweather.domain.AppSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainInteractor @Inject constructor(mainDataRepository: MainDataRepository, val weatherService: WeatherService) {

    private val mainRepository : MainRepository = mainDataRepository

    fun observeAppSection() = mainRepository.observe()

    fun setAppSection(appSection: AppSection) = mainRepository.setAndObserveSingle(appSection)
}
