package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.data.repositories.main.AppSectionDataRepository
import com.gmail.segenpro.myweather.data.repositories.main.ReloadContentDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.WeatherDataRepository
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.Repository
import com.gmail.segenpro.myweather.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class RepositoryModule {

    @Binds
    @Named("appSection")
    abstract fun bindAppSectionRepository(appSectionDataRepository: AppSectionDataRepository): Repository<AppSection> /*MainRepository*/

    @Binds
    @Named("reload")
    abstract fun bindReloadContentRepository(reloadContentDataRepository: ReloadContentDataRepository): Repository<AppSection>

    @Binds
    abstract fun bindWeatherRepository(weatherDataRepository: WeatherDataRepository): WeatherRepository
}
