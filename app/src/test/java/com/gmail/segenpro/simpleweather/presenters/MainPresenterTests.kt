package com.gmail.segenpro.simpleweather.presenters

import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
import com.gmail.segenpro.simpleweather.presentation.main.MainPresenter
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import com.google.gson.Gson
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests : BaseTestsClass() {

    companion object {
        const val LOCATION_STRING = ""

        val SEARCH_LOCATION = SearchLocation(0, "", "", "", 0f,
                0f, "")
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var gson: Gson

    @Mock
    lateinit var locationInteractor: LocationInteractor

    private lateinit var mainPresenter: MainPresenter

    @Before
    fun setup() {
        mainPresenter = MainPresenter(gson, locationInteractor)
    }

    @Test
    fun searchLocationSavedIfLocationStringSet() {
        `when`(locationInteractor.setCurrentLocation(any())).thenReturn(Completable.complete())
        `when`(gson.fromJson(anyString(), any<Class<SearchLocation>>())).thenReturn(SEARCH_LOCATION)

        mainPresenter.setLocation(LOCATION_STRING)

        verify(locationInteractor).setCurrentLocation(SEARCH_LOCATION)
    }
}
