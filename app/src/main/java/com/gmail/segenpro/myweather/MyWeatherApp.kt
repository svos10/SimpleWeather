package com.gmail.segenpro.myweather

import android.app.Application
import android.content.Context
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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
        // во избежание проблем с ContentProvider для сохранения instance использован метод
        // Application.attachBaseContext() вместо Application.onCreate(), т.к последовательность
        // вызовов следующая: Application.attachBaseContext(), ContentProvider.onCreate(), Application.onCreate()
    }

    companion object {
        lateinit var instance: MyWeatherApp
    }
}
