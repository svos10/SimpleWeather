package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.MyWeatherApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(context: MyWeatherApp)//todo поменять
}