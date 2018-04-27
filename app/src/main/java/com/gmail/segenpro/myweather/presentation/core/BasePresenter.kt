package com.gmail.segenpro.myweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.di.AppComponent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    @Inject
    protected lateinit var router: Router

    init {
        @Suppress("LeakingThis")
        inject(MyWeatherApp.instance.component)
    }

    abstract fun inject(appComponent: AppComponent)
}
