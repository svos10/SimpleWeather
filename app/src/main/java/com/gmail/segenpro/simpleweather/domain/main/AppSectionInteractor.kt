package com.gmail.segenpro.simpleweather.domain.main

import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.Repository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AppSectionInteractor @Inject constructor() {

    @Inject
    @field:Named("appSection")
    lateinit var appSectionRepository: Repository<AppSection>

    fun observeAppSection() = appSectionRepository.observe()

    fun observeAppSectionOnce() = appSectionRepository.observeSingle()

    fun setAppSection(appSection: AppSection) = appSectionRepository.setAndObserveSingle(appSection)
}
