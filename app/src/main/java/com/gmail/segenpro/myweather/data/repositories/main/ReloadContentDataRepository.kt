package com.gmail.segenpro.myweather.data.repositories.main

import com.gmail.segenpro.myweather.data.repositories.core.DirectItemRepository
import com.gmail.segenpro.myweather.domain.AppSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReloadContentDataRepository @Inject constructor() : DirectItemRepository<AppSection>()
