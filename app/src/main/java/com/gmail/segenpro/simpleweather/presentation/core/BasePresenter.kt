package com.gmail.segenpro.simpleweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.onError()
    }

    protected fun Disposable.unsubscribeOnDestroy(): Disposable = apply {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
