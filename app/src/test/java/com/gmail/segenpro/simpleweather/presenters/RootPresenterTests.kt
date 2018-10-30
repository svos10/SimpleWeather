package com.gmail.segenpro.simpleweather.presenters

import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.domain.main.AppSectionInteractor
import com.gmail.segenpro.simpleweather.presentation.core.rootfragment.RootPresenter
import com.gmail.segenpro.simpleweather.presentation.core.rootfragment.RootView
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Router

@RunWith(MockitoJUnitRunner::class)
class RootPresenterTests : BaseTestsClass() {

    companion object {
        val CURRENT_APP_SECTION = AppSection.FORECAST
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var locationInteractor: LocationInteractor

    @Mock
    lateinit var appSectionInteractor: AppSectionInteractor

    @Mock
    lateinit var router: Router

    @Mock
    lateinit var rootView: RootView

    private lateinit var rootPresenter: RootPresenter

    @Before
    fun setup() {
        rootPresenter = RootPresenter(locationInteractor, appSectionInteractor, router)
    }

    @Test
    fun selectTabBarButtonIfAppSectionReceived() {
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.just(CURRENT_APP_SECTION))
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.empty())

        rootPresenter.attachView(rootView)

        verify(rootView).selectTabBarButton(CURRENT_APP_SECTION)
    }

    @Test
    fun goToScreenIfAppSectionReceived() {
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.just(CURRENT_APP_SECTION))
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.empty())

        rootPresenter.attachView(rootView)

        verify(router).replaceScreen(CURRENT_APP_SECTION.name)
    }

    @Test
    fun showLocationNameIfLocationChanged() {
        val location = Location()
        val locationResult: Result<Location> = location.asResult()
        `when`(appSectionInteractor.observeAppSection()).thenReturn(Observable.empty())
        `when`(locationInteractor.getCurrentLocation()).thenReturn(Observable.just(locationResult))

        rootPresenter.attachView(rootView)

        verify(rootView).showLocationName(location.name)
    }

    @Test
    fun setAppSectionIfThisIsOtherSection() {
        `when`(appSectionInteractor.observeAppSectionOnce()).thenReturn(Single.just(CURRENT_APP_SECTION))
        `when`(appSectionInteractor.setAppSection(any())).thenReturn(Single.never())

        rootPresenter.openHistory()

        verify(appSectionInteractor).setAppSection(any())
    }

    @Test
    fun doNotSetAppSectionIfThisIsCurrentSection() {
        `when`(appSectionInteractor.observeAppSectionOnce()).thenReturn(Single.just(CURRENT_APP_SECTION))

        rootPresenter.openForecast()

        verify(appSectionInteractor, never()).setAppSection(any())
    }
}
