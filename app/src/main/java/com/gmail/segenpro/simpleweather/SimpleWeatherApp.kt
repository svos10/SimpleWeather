package com.gmail.segenpro.simpleweather

import android.app.Application
import android.content.Context
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.gmail.segenpro.simpleweather.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class SimpleWeatherApp : Application() {

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

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    companion object {
        lateinit var instance: SimpleWeatherApp
    }
}
