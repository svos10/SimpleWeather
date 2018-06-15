package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.data.repositories.main.AppSectionDataRepository
import com.gmail.segenpro.myweather.data.repositories.main.ReloadContentDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.ForecastDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.HistoryDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.LocationDataRepository
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.Repository
import com.gmail.segenpro.myweather.domain.weather.ForecastRepository
import com.gmail.segenpro.myweather.domain.weather.HistoryRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class RepositoryModule {

    @Binds
    @Named("appSection")
    abstract fun bindAppSectionRepository(appSectionDataRepository: AppSectionDataRepository): Repository<AppSection>

    @Binds
    @Named("reload")
    abstract fun bindReloadContentRepository(reloadContentDataRepository: ReloadContentDataRepository): Repository<AppSection>

    @Binds
    @Named("currentLocation")
    abstract fun bindCurrentLocationRepository(currentLocationDataRepository: LocationDataRepository): Repository<Long>

    @Binds
    abstract fun bindForecastRepository(forecastDataRepository: ForecastDataRepository): ForecastRepository

    @Binds
    abstract fun bindHistoryRepository(historyDataRepository: HistoryDataRepository): HistoryRepository
}
