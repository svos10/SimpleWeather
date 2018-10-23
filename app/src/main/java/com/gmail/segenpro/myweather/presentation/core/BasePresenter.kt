package com.gmail.segenpro.myweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.location.LocationInteractor
import com.gmail.segenpro.myweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.myweather.domain.main.ReloadContentInteractor
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
        inject(MyWeatherApp.instance.component)
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
