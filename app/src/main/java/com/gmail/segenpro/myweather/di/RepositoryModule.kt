package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.data.repositories.main.MainDataRepository
import com.gmail.segenpro.myweather.data.repositories.weather.WeatherDataRepository
import com.gmail.segenpro.myweather.domain.main.MainRepository
import com.gmail.segenpro.myweather.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(mainDataRepository: MainDataRepository): MainRepository

    @Binds
    abstract fun bindWeatherRepository(weatherDataRepository: WeatherDataRepository): WeatherRepository
}
