package com.gmail.segenpro.myweather

import android.app.Application
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.di.DaggerAppComponent

class MyWeatherApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .context(this.applicationContext)
                .build()
    }

    companion object {
         lateinit var instance : MyWeatherApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
