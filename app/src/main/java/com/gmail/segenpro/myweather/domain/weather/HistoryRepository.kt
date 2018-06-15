package com.gmail.segenpro.myweather.domain.weather

import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.domain.core.models.Location
import io.reactivex.Completable
import io.reactivex.Single

interface HistoryRepository: BaseRepository {

    fun getHistoryFromServer(locationName: String, historyDate: String): Single<Result<HistoryDay>>

    fun getHistoryFromDb(location: Location): Single<List<HistoryDay>>

    fun putHistoryDayToDb(historyDay: HistoryDay): Completable

    fun deleteOldHistoryFromDb(location: Location, maxKeepCount: Int): Completable
}
