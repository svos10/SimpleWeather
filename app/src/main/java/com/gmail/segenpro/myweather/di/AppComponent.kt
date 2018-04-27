package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.presentation.charts.ChartsPresenter
import com.gmail.segenpro.myweather.presentation.forecast.ForecastPresenter
import com.gmail.segenpro.myweather.presentation.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(presenter: MainPresenter)

    fun inject(presenter: ForecastPresenter)

    fun inject(presenter: ChartsPresenter)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(myWeatherApp: MyWeatherApp): Builder
    }
}
