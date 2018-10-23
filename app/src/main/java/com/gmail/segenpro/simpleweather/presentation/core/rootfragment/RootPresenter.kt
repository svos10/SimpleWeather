package com.gmail.segenpro.simpleweather.presentation.core.rootfragment

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.presentation.core.BasePresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RootPresenter : BasePresenter<RootView>() {

    @Inject
    lateinit var router: Router

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onFirstViewAttach() {
        appSectionInteractor.observeAppSection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.selectAppSection(it)
                    router.replaceScreen(it.name)
                }, { onError(it) })
                .unsubscribeOnDestroy()

        showCurrentLocation()
    }

    private fun showCurrentLocation() {
        locationInteractor.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val name = (it as? Result.Success)?.data?.name ?: ""
                    viewState.showLocationName(name)
                }, { onError(it) })
                .unsubscribeOnDestroy()
    }

    fun openForecast() = open(AppSection.FORECAST)

    fun openHistory() = open(AppSection.HISTORY)

    private fun open(appSection: AppSection) = appSectionInteractor.observeAppSectionOnce()
            .flatMapCompletable {
                if (it != appSection) {
                    appSectionInteractor.setAppSection(appSection).ignoreElement()
                } else {
                    Completable.complete()
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe({}, { onError(it) })
            .unsubscribeOnDestroy()
}
