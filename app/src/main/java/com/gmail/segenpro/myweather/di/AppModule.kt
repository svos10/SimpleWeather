package com.gmail.segenpro.myweather.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gmail.segenpro.myweather.MyWeatherApp
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(myWeatherApp: MyWeatherApp) : Context = myWeatherApp.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) : SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideRxSharedPreferences(sharedPreferences: SharedPreferences) : RxSharedPreferences =
            RxSharedPreferences.create(sharedPreferences)

    @Provides
    @Singleton
    fun provideRouter(myWeatherApp: MyWeatherApp) : Router = myWeatherApp.cicerone.router
}