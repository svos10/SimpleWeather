package com.gmail.segenpro.myweather.presentation.core

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.myweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.myweather.domain.weather.WeatherInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    @Inject
    lateinit var context: Context

    @Inject
    protected lateinit var router: Router

    @Inject
    lateinit var weatherInteractor: WeatherInteractor

    @Inject
    lateinit var appSectionInteractor: AppSectionInteractor

    @Inject
    lateinit var reloadContentInteractor: ReloadContentInteractor

    private val compositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        inject(MyWeatherApp.instance.component)
    }

    abstract fun inject(appComponent: AppComponent)

    protected fun Disposable.unsubscribeOnDestroy(): Disposable = apply {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
