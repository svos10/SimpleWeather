package com.gmail.segenpro.simpleweather.data.repositories.main

import com.gmail.segenpro.simpleweather.data.repositories.core.DirectItemRepository
import com.gmail.segenpro.simpleweather.domain.AppSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReloadContentDataRepository @Inject constructor() : DirectItemRepository<AppSection>()
