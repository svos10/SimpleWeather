package com.gmail.segenpro.simpleweather.repositories

import android.content.Context
import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.ErrorType
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.WeatherException
import com.gmail.segenpro.simpleweather.data.database.WeatherHistoryDao
import com.gmail.segenpro.simpleweather.data.database.entities.LocationEntity
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.gmail.segenpro.simpleweather.data.network.dto.SearchLocationDto
import com.gmail.segenpro.simpleweather.data.repositories.location.LocationDataRepository
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import com.gmail.segenpro.simpleweather.rules.RxImmediateSchedulerRule
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationDataRepositoryTests : BaseTestsClass() {

    companion object {
        private const val LOCATION_ID = 10L
        const val LOCATION_NAME = ""
        val LOCATION = Location(id = LOCATION_ID)
        val LOCATION_ENTITY = LocationEntity(LOCATION_ID, "", "", "",
                0f, 0f, "")
        val SEARCH_LOCATION = SearchLocation(10L, "", "", "", 0f,
                0f, "")
        val WEATHER_EXCEPTION = WeatherException(ErrorType.LOCATION_NOT_SELECTED)
        val SEARCH_LOCATION_DTO_LIST = arrayListOf(SearchLocationDto(10L, "", "",
                "", 0f, 0f, ""))
        val INCORRECT_SEARCH_LOCATION_DTO_LIST = arrayListOf(SearchLocationDto(null, "", "",
                "", 0f, 0f, ""))
        val SEARCH_LOCATION_LIST = arrayListOf(SearchLocation(10L, "", "", "",
                0f, 0f, ""))
    }

    @Rule
    @JvmField
    val schedulerRule = RxImmediateSchedulerRule(false)

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var gson: Gson

    @Mock
    lateinit var weatherService: WeatherService

    @Mock
    lateinit var weatherHistoryDao: WeatherHistoryDao

    @Mock
    lateinit var dtoToSearchLocationMapper: Mapper<SearchLocationDto, SearchLocation>

    @Mock
    lateinit var searchLocationToLocationEntityMapper: Mapper<SearchLocation, LocationEntity>

    @Mock
    lateinit var toLocationModelMapper: Mapper<LocationEntity, Location>

    private lateinit var locationDataRepository: LocationDataRepository

    @Before
    fun setup() {
        locationDataRepository = LocationDataRepository(context, gson, weatherService, weatherHistoryDao,
                dtoToSearchLocationMapper, searchLocationToLocationEntityMapper, toLocationModelMapper)
    }

    @Test
    fun getLocationFromDbIfGetLocationId() {
        `when`(weatherHistoryDao.getLocationEntity(anyLong())).thenReturn(LOCATION_ENTITY)
        `when`(toLocationModelMapper.map(any<LocationEntity>())).thenReturn(LOCATION)

        val observer = locationDataRepository.getLocationFromDb(LOCATION_ID).test()

        observer.assertValue { it == LOCATION.asResult() }
    }

    @Test
    fun getExceptionIfLocationEntityIsAbsent() {
        `when`(weatherHistoryDao.getLocationEntity(anyLong())).thenReturn(null)

        val observer = locationDataRepository.getLocationFromDb(LOCATION_ID).test()

        observer.assertValue { it == WEATHER_EXCEPTION.asErrorResult<Location>() }
    }

    @Test
    fun putLocationToDbIfGetSearchLocation() {
        `when`(searchLocationToLocationEntityMapper.map(any<SearchLocation>())).thenReturn(LOCATION_ENTITY)

        locationDataRepository.putLocationToDb(SEARCH_LOCATION).test()

        verify(weatherHistoryDao).upsert(LOCATION_ENTITY)
    }

    @Test
    fun getSearchLocationListIfGetLocationName() {
        `when`(weatherService.searchLocation(anyString())).thenReturn(Single.just(SEARCH_LOCATION_DTO_LIST))
        `when`(dtoToSearchLocationMapper.map(any<SearchLocationDto>())).thenReturn(SEARCH_LOCATION)

        val observer = locationDataRepository.searchLocationsOnServer(LOCATION_NAME).test()

        observer.assertValue { it == SEARCH_LOCATION_LIST.asResult() }
    }

    @Test
    fun getSearchLocationListExceptionIfIncorrectServerData() {
        `when`(weatherService.searchLocation(anyString())).thenReturn(Single.just(INCORRECT_SEARCH_LOCATION_DTO_LIST))
        `when`(context.getString(anyInt())).thenReturn("")

        val observer = locationDataRepository.searchLocationsOnServer(LOCATION_NAME).test()

        observer.assertValue { it is Result.Error }
    }
}
