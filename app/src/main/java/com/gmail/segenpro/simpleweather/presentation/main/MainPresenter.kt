package com.gmail.segenpro.simpleweather.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.presentation.core.BasePresenter
import com.google.gson.Gson
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val gson: Gson,
                                        private val locationInteractor: LocationInteractor) : BasePresenter<MainView>() {

    private var locationDisposable = Disposables.empty()

    fun setLocation(searchLocationString: String) {
        if (!locationDisposable.isDisposed) locationDisposable.dispose()
        locationDisposable = locationInteractor.setCurrentLocation(gson.fromJson(searchLocationString, SearchLocation::class.java))
                .subscribeOn(Schedulers.io())
                .subscribe({}, { onError(it) })
                .unsubscribeOnDestroy()
    }
}
