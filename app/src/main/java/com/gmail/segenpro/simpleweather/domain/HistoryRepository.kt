package com.gmail.segenpro.simpleweather.domain

import com.gmail.segenpro.simpleweather.data.network.Result
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.domain.core.models.Location
import io.reactivex.Completable
import io.reactivex.Single

interface HistoryRepository {

    fun getHistoryFromServer(locationName: String, historyDate: String): Single<Result<HistoryDay>>

    fun getHistoryFromDb(location: Location): Single<List<HistoryDay>>

    fun putHistoryDayToDb(historyDay: HistoryDay): Completable

    fun deleteOldHistoryFromDb(location: Location, maxKeepCount: Int): Completable
}
