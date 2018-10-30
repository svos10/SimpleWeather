package com.gmail.segenpro.simpleweather.presentation.core

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun onUnexpectedError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.onUnexpectedError()
    }

    protected fun Disposable.unsubscribeOnDestroy(): Disposable = apply {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
