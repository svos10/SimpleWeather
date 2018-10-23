package com.gmail.segenpro.myweather.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.myweather.di.AppComponent
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
import com.gmail.segenpro.myweather.presentation.core.BasePresenter
import com.google.gson.Gson
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    @Inject
    lateinit var gson: Gson

    private var locationDisposable = Disposables.empty()

    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    fun setLocation(searchLocationString: String) {
        if (!locationDisposable.isDisposed) locationDisposable.dispose()
        locationDisposable = locationInteractor.setCurrentLocation(gson.fromJson(searchLocationString, SearchLocation::class.java))
                .subscribeOn(Schedulers.io())
                .subscribe({}, { onError(it) })
                .unsubscribeOnDestroy()
    }
}
