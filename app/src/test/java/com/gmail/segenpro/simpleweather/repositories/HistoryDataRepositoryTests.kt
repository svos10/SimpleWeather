package com.gmail.segenpro.simpleweather.repositories

import android.content.Context
import com.gmail.segenpro.simpleweather.BaseTestsClass
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.WeatherHistoryDao
import com.gmail.segenpro.simpleweather.data.database.entities.*
import com.gmail.segenpro.simpleweather.data.database.pojo.HistoryEntitiesWrapper
import com.gmail.segenpro.simpleweather.data.database.pojo.HistoryObject
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.WeatherService
import com.gmail.segenpro.simpleweather.data.network.dto.*
import com.gmail.segenpro.simpleweather.data.repositories.weather.HistoryDataRepository
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDays
import com.gmail.segenpro.simpleweather.domain.core.models.Location
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
class HistoryDataRepositoryTests : BaseTestsClass() {

    companion object {
        const val LOCATION_NAME = ""
        const val HISTORY_DATE = ""
        const val MAX_KEEP_COUNT = 1
        val LOCATION = Location()
        val HISTORY_DAY = HistoryDay()
        val HISTORY_DAY_LIST = arrayListOf(HISTORY_DAY)
        val HISTORY_DAYS = HistoryDays()
        private val LOCATION_DTO = LocationDto(0f, 0f, "", "",
                "", "", 0, "")
        private val CONDITION_DTO = ConditionDto("", "", 0)
        private val DAY_DTO = DayDto(0f, 0f, 0f,
                0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0, CONDITION_DTO, 0f)
        private val ASTRO_DTO = AstroDto("", "", "", "", "",
                "")
        private val HOUR_DTO = HourDto(0, "", 0f, 0f, 0f,
                0f, 0, "", 0f, 0f,
                0f, 0f, 0, 0,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0, 0, 0, 0f, 0f, CONDITION_DTO)
        private val HISTORY_DAY_DETAILS_DTO = HistoryDayDetailsDto("", 0, DAY_DTO, ASTRO_DTO,
                arrayListOf(HOUR_DTO))
        private val HISTORY_DAYS_DTO = HistoryDaysDto(arrayListOf(HISTORY_DAY_DETAILS_DTO))
        val HISTORY_RESPONSE_DTO = HistoryResponseDto(LOCATION_DTO, HISTORY_DAYS_DTO)
        private val INCORRECT_LOCATION_DTO = LocationDto(null, 0f, "", "",
                "", "", 0, "")
        val INCORRECT_HISTORY_RESPONSE_DTO = HistoryResponseDto(INCORRECT_LOCATION_DTO, HISTORY_DAYS_DTO)
        private val HISTORY_DAY_ENTITY = HistoryDayEntity(0L, 0, 0L, 0, "",
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0, 0f, 0, "")
        private val CONDITION_ENTITY = ConditionEntity(0, "", "")
        private val ASTRO_ENTITY = AstroEntity(0L, 0L, "", "", "",
                "", "", "")
        private val LOCATION_ENTITY = LocationEntity(0L, "", "", "", 0f,
                0f, "")
        private val HOUR_ENTITY = HourEntity(0L, 0L, 0, 0, "",
                0f, 0f, 0f, 0f,
                0, "", 0f, 0f,
                0f, 0f, 0, 0,
                0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, true, true, true, 0f,
                0f)
        val HISTORY_ENTITIES_WRAPPER = HistoryEntitiesWrapper(HISTORY_DAY_ENTITY, CONDITION_ENTITY,
                ASTRO_ENTITY, LOCATION_ENTITY, arrayListOf(HOUR_ENTITY), arrayListOf(CONDITION_ENTITY))
        val HISTORY_OBJECT_LIST = arrayListOf(HistoryObject())
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
    lateinit var dtoToHistoryDaysMapper: Mapper<HistoryResponseDto, HistoryDays>

    @Mock
    lateinit var modelToHistoryEntitiesWrapperMapper: Mapper<HistoryDay, HistoryEntitiesWrapper>

    @Mock
    lateinit var objectToHistoryDayMapper: Mapper<HistoryObject, HistoryDay>

    @Mock
    lateinit var locationToLocationEntityMapper: Mapper<Location, LocationEntity>

    private lateinit var historyDataRepository: HistoryDataRepository

    @Before
    fun setup() {
        historyDataRepository = HistoryDataRepository(context, gson, weatherService, weatherHistoryDao,
                dtoToHistoryDaysMapper, modelToHistoryEntitiesWrapperMapper, objectToHistoryDayMapper,
                locationToLocationEntityMapper)
    }

    @Test
    fun getHistoryFromServerIfGetLocationNameAndHistoryDate() {
        `when`(weatherService.getHistory(anyString(), anyString())).thenReturn(Single.just(HISTORY_RESPONSE_DTO))
        `when`(dtoToHistoryDaysMapper.map(any<HistoryResponseDto>())).thenReturn(HISTORY_DAYS)

        val observer = historyDataRepository.getHistoryFromServer(LOCATION_NAME, HISTORY_DATE).test()

        observer.assertValue { it == HISTORY_DAY.asResult() }
    }

    @Test
    fun getHistoryFromServerExceptionIfIncorrectServerData() {
        `when`(weatherService.getHistory(anyString(), anyString())).thenReturn(Single.just(INCORRECT_HISTORY_RESPONSE_DTO))
        `when`(context.getString(anyInt())).thenReturn("")

        val observer = historyDataRepository.getHistoryFromServer(LOCATION_NAME, HISTORY_DATE).test()

        observer.assertValue { it is Result.Error }
    }

    @Test
    fun putHistoryDayToDb() {
        `when`(modelToHistoryEntitiesWrapperMapper.map(any<HistoryDay>())).thenReturn(HISTORY_ENTITIES_WRAPPER)

        historyDataRepository.putHistoryDayToDb(HISTORY_DAY).test()

        verify(weatherHistoryDao).insertHistoryDay(HISTORY_ENTITIES_WRAPPER)
    }

    @Test
    fun deleteOldHistoryFromDbIfGetLocationAndMaxKeepCount() {
        `when`(locationToLocationEntityMapper.map(any<Location>())).thenReturn(LOCATION_ENTITY)

        historyDataRepository.deleteOldHistoryFromDb(LOCATION, MAX_KEEP_COUNT).test()

        verify(weatherHistoryDao).deleteOldHistory(LOCATION_ENTITY, MAX_KEEP_COUNT)
    }

    @Test
    fun getHistoryFromDbIfGetLocation() {
        `when`(weatherHistoryDao.getHistory(LOCATION_ENTITY)).thenReturn(HISTORY_OBJECT_LIST)
        `when`(locationToLocationEntityMapper.map(any<Location>())).thenReturn(LOCATION_ENTITY)
        `when`(objectToHistoryDayMapper.map(any<HistoryObject>())).thenReturn(HISTORY_DAY)

        val observer = historyDataRepository.getHistoryFromDb(LOCATION).test()

        observer.assertValue { it == HISTORY_DAY_LIST }
    }
}
