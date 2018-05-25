package com.gmail.segenpro.myweather.domain.main

import com.gmail.segenpro.myweather.domain.AppSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainInteractor @Inject constructor(private val mainRepository: MainRepository) {

    fun observeAppSection() = mainRepository.observe()

    fun setAppSection(appSection: AppSection) = mainRepository.setAndObserveSingle(appSection)
}
