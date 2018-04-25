package com.gmail.segenpro.myweather

import android.app.Application
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.di.AppModule
import com.gmail.segenpro.myweather.di.DaggerAppComponent

class MyWeatherApp : Application() {

    private val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}