package com.gmail.segenpro.myweather.presentation.core.rootfragment

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.myweather.domain.main.ReloadContentInteractor
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import com.gmail.segenpro.myweather.presentation.utils.isNetworkAvailable
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class RootPresenter : BasePresenter<RootView>() {

    @Inject
    lateinit var appSectionInteractor: AppSectionInteractor

    @Inject
    lateinit var reloadContentInteractor: ReloadContentInteractor

    @Inject
    lateinit var context: Context

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onFirstViewAttach() = appSectionInteractor.observeAppSection()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                viewState.hideError()
                viewState.showProgress(false)
            }
            .subscribe({
                viewState.selectAppSection(it)
                router.replaceScreen(it.name)
            })
            .unsubscribeOnDestroy()

    fun openForecast() = open(AppSection.FORECAST)

    fun openCharts() = open(AppSection.CHARTS)

    fun onTryAgainClicked() {
        if (!isNetworkAvailable(context)) return

        appSectionInteractor.observeAppSectionOnce()
                .flatMapCompletable { reloadContentInteractor.requestReloadContent(it).toCompletable() }
                .subscribe()
                .unsubscribeOnDestroy()
    }

    private fun open(appSection: AppSection) = appSectionInteractor.observeAppSectionOnce()
            .flatMapCompletable {
                if (it != appSection) {
                    appSectionInteractor.setAppSection(appSection).toCompletable()
                } else {
                    Completable.complete()
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
            .unsubscribeOnDestroy()
}
