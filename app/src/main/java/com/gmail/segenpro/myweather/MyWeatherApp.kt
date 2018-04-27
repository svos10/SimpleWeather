package com.gmail.segenpro.myweather

import android.app.Application
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.di.DaggerAppComponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MyWeatherApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .app(this)
                .build()
    }

    val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyWeatherApp
    }
}
