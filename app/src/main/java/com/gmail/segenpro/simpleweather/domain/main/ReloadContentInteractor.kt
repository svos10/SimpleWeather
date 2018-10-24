package com.gmail.segenpro.simpleweather.domain.main

import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.Repository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ReloadContentInteractor @Inject constructor(@Named("reload") private val reloadContentRepository: Repository<AppSection>) {

    fun observeReloadContentRequest() = reloadContentRepository.observe()

    fun observeReloadContentRequestOnce() = reloadContentRepository.observeSingle()

    fun requestReloadContent(appSection: AppSection) = reloadContentRepository.setAndObserveSingle(appSection)
}
