package com.gmail.segenpro.simpleweather.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.data.database.WeatherHistoryDatabase
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

private val DATABASE_NAME = WeatherHistoryDatabase::class.java.simpleName

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(simpleWeatherApp: SimpleWeatherApp): Context = simpleWeatherApp.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideRxSharedPreferences(sharedPreferences: SharedPreferences) =
            RxSharedPreferences.create(sharedPreferences)

    @Provides
    @Singleton
    fun provideRouter(simpleWeatherApp: SimpleWeatherApp): Router = simpleWeatherApp.cicerone.router

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, WeatherHistoryDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun provideWeatherHistoryDao(database: WeatherHistoryDatabase) = database.weatherHistoryDao()
}
