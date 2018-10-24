package com.gmail.segenpro.simpleweather.di

import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.presentation.core.rootfragment.RootFragment
import com.gmail.segenpro.simpleweather.presentation.forecast.ForecastFragment
import com.gmail.segenpro.simpleweather.presentation.history.HistoryFragment
import com.gmail.segenpro.simpleweather.presentation.location.SuggestionsProvider
import com.gmail.segenpro.simpleweather.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, MappersModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: RootFragment)

    fun inject(fragment: ForecastFragment)

    fun inject(fragment: HistoryFragment)

    fun inject(contentProvider: SuggestionsProvider)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(simpleWeatherApp: SimpleWeatherApp): Builder
    }
}
