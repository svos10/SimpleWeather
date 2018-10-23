package com.gmail.segenpro.simpleweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.domain.main.ReloadContentInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    @Inject
    protected lateinit var locationInteractor: LocationInteractor

    @Inject
    protected lateinit var reloadContentInteractor: ReloadContentInteractor

    @Inject
    lateinit var appSectionInteractor: AppSectionInteractor

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        inject(SimpleWeatherApp.instance.component)
    }

    abstract fun inject(appComponent: AppComponent)

    protected fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    protected fun Disposable.unsubscribeOnDestroy(): Disposable = apply {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
