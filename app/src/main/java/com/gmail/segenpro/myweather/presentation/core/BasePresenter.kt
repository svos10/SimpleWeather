package com.gmail.segenpro.myweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.di.AppComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    @Inject
    protected lateinit var router: Router

    private val compositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        inject(MyWeatherApp.instance.component)
    }

    abstract fun inject(appComponent: AppComponent)

    protected fun Disposable.unsubscribeOnDestroy() {
        compositeDisposable.add(this)
    }

    protected fun onError(error: Throwable) {
        error.printStackTrace()
        viewState.showError(error)
    }

    protected fun showProgress(isShown: Boolean) {
        viewState.showProgress(isShown)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
