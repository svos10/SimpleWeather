package com.gmail.segenpro.simpleweather.di

import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.presentation.core.rootfragment.RootPresenter
import com.gmail.segenpro.simpleweather.presentation.forecast.ForecastPresenter
import com.gmail.segenpro.simpleweather.presentation.history.HistoryPresenter
import com.gmail.segenpro.simpleweather.presentation.location.SuggestionsProvider
import com.gmail.segenpro.simpleweather.presentation.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, MappersModule::class])
interface AppComponent {

    fun inject(presenter: MainPresenter)

    fun inject(presenter: RootPresenter)

    fun inject(presenter: ForecastPresenter)

    fun inject(presenter: HistoryPresenter)

    fun inject(contentProvider: SuggestionsProvider)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(simpleWeatherApp: SimpleWeatherApp): Builder
    }
}
