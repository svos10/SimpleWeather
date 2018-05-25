package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.presentation.core.rootfragment.RootPresenter
import com.gmail.segenpro.myweather.presentation.forecast.ForecastPresenter
import com.gmail.segenpro.myweather.presentation.history.HistoryPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(presenter: RootPresenter)

    fun inject(presenter: ForecastPresenter)

    fun inject(presenter: HistoryPresenter)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(myWeatherApp: MyWeatherApp): Builder
    }
}
