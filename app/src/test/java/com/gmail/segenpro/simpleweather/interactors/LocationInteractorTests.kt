package com.gmail.segenpro.simpleweather.interactors

import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.domain.CURRENT_LOCATION_ID_NOT_SET
import com.gmail.segenpro.simpleweather.domain.LocationRepository
import com.gmail.segenpro.simpleweather.domain.core.Repository
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import com.gmail.segenpro.simpleweather.domain.location.LocationInteractor
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

@RunWith(MockitoJUnitRunner::class)
class LocationInteractorTests : BaseTestsClass() {

    companion object {
        const val CURRENT_LOCATION_ID = 10L
        val CURRENT_LOCATION = Location()
        val SEARCH_LOCATION = SearchLocation(100L, "", "", "", 0f,
                0f, "")
        val SEARCH_LOCATION_EQUAL_CURRENT = SearchLocation(10L, "", "", "",
                0f, 0f, "")
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var locationRepository: LocationRepository

    @Mock
    lateinit var repository: Repository<Long>

    private lateinit var locationInteractor: LocationInteractor

    @Before
    fun setup() {
        locationInteractor = LocationInteractor(locationRepository, repository)
    }

    @Test
    fun getLocationFromDbIfGetLocationId() {
        `when`(repository.observe()).thenReturn(Observable.just(CURRENT_LOCATION_ID))
        `when`(locationRepository.getLocationFromDb(anyLong()))
                .thenReturn(Single.just(CURRENT_LOCATION.asResult()))

        val observer = locationInteractor.getCurrentLocation().test()

        observer.assertValue { it == CURRENT_LOCATION.asResult() }
    }

    @Test
    fun getExceptionIfLocationIdNotSet() {
        `when`(repository.observe()).thenReturn(Observable.just(CURRENT_LOCATION_ID_NOT_SET))

        val observer = locationInteractor.getCurrentLocation().test()

        observer.assertValue { it == WeatherException(ErrorType.LOCATION_NOT_SELECTED).asErrorResult<Location>() }
    }

    @Test
    fun setLocationToDbAndAsCurrentIfErrorCurrentLocation() {
        `when`(repository.observe()).thenReturn(Observable.just(CURRENT_LOCATION_ID_NOT_SET))

        locationInteractor.setCurrentLocation(SEARCH_LOCATION).test()

        verify(locationRepository).putLocationToDb(SEARCH_LOCATION)
        verify(repository).setAndObserveSingle(SEARCH_LOCATION.id)
    }

    @Test
    fun setLocationToDbAndAsCurrentIfDifferentLocations() {
        `when`(repository.observe()).thenReturn(Observable.just(CURRENT_LOCATION_ID))
        `when`(locationRepository.getLocationFromDb(anyLong()))
                .thenReturn(Single.just(CURRENT_LOCATION.asResult()))

        locationInteractor.setCurrentLocation(SEARCH_LOCATION).test()

        verify(locationRepository).putLocationToDb(SEARCH_LOCATION)
        verify(repository).setAndObserveSingle(SEARCH_LOCATION.id)
    }

    @Test
    fun ignoreSearchLocationIfCurrentLocationAndSearchLocationAreSame() {
        `when`(repository.observe()).thenReturn(Observable.just(CURRENT_LOCATION_ID))
        `when`(locationRepository.getLocationFromDb(anyLong()))
                .thenReturn(Single.just(CURRENT_LOCATION.asResult()))

        locationInteractor.setCurrentLocation(SEARCH_LOCATION_EQUAL_CURRENT).test()

        verify(locationRepository, never()).putLocationToDb(SEARCH_LOCATION)
        verify(repository, never()).setAndObserveSingle(SEARCH_LOCATION.id)
    }
}
