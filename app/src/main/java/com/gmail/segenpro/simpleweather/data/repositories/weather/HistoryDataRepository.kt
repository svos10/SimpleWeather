package com.gmail.segenpro.simpleweather.data.repositories.weather

import com.gmail.segenpro.simpleweather.asErrorResult
import com.gmail.segenpro.simpleweather.asResult
import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.entities.LocationEntity
import com.gmail.segenpro.simpleweather.data.database.pojo.HistoryEntitiesWrapper
import com.gmail.segenpro.simpleweather.data.database.pojo.HistoryObject
import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.data.network.dto.HistoryResponseDto
import com.gmail.segenpro.simpleweather.data.repositories.BaseDataRepository
import com.gmail.segenpro.simpleweather.data.retrofitResponseToResult
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDays
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import com.gmail.segenpro.simpleweather.domain.HistoryRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryDataRepository @Inject constructor(private val dtoToHistoryDaysMapper: Mapper<HistoryResponseDto, HistoryDays>,
                                                private val modelToHistoryEntitiesWrapperMapper: Mapper<HistoryDay, HistoryEntitiesWrapper>,
                                                private val objectToHistoryDayMapper: Mapper<HistoryObject, HistoryDay>,
                                                private val locationToLocationEntityMapper: Mapper<Location, LocationEntity>) :
        BaseDataRepository(), HistoryRepository {

    override fun getHistoryFromServer(locationName: String, historyDate: String): Single<Result<HistoryDay>> =
            weatherService.getHistory(locationName, historyDate)
                    .retrofitResponseToResult(context, gson)
                    .map {
                        when (it) {
                            is Result.Success -> dtoToHistoryDaysMapper.map(it.data).historyDayList[0].asResult()

                            is Result.Error -> it.weatherException.asErrorResult()
                        }
                    }

    override fun putHistoryDayToDb(historyDay: HistoryDay): Completable = Completable.fromCallable {
        weatherHistoryDao.insertHistoryDay(modelToHistoryEntitiesWrapperMapper.map(historyDay))
    }

    override fun deleteOldHistoryFromDb(location: Location, maxKeepCount: Int): Completable =
            Completable.fromAction {
                val locationEntity = locationToLocationEntityMapper.map(location)
                weatherHistoryDao.deleteOldHistory(locationEntity, maxKeepCount)
            }

    override fun getHistoryFromDb(location: Location): Single<List<HistoryDay>> =
            Single.defer {
                weatherHistoryDao.getHistory(locationToLocationEntityMapper.map(location)).toObservable()
                        .map { objectToHistoryDayMapper.map(it) }
                        .toList()
            }
}
