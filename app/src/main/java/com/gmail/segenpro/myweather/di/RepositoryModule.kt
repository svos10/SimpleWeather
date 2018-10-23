package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.data.repositories.main.AppSectionDataRepository
import com.gmail.segenpro.myweather.data.repositories.main.ReloadContentDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.ForecastDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.HistoryDataRepository
import com.gmail.segenpro.myweather.data.repositories.location.CurrentLocationIdDataRepository
import com.gmail.segenpro.myweather.data.repositories.location.LocationDataRepository
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.core.Repository
import com.gmail.segenpro.myweather.domain.ForecastRepository
import com.gmail.segenpro.myweather.domain.HistoryRepository
import com.gmail.segenpro.myweather.domain.LocationRepository
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
    @Named("currentLocationId")
    abstract fun bindCurrentLocationIdRepository(currentLocationIdDataRepository: CurrentLocationIdDataRepository): Repository<Long>

    @Binds
    abstract fun bindLocationRepository(locationDataRepository: LocationDataRepository): LocationRepository

    @Binds
    abstract fun bindForecastRepository(forecastDataRepository: ForecastDataRepository): ForecastRepository

    @Binds
    abstract fun bindHistoryRepository(historyDataRepository: HistoryDataRepository): HistoryRepository
}
