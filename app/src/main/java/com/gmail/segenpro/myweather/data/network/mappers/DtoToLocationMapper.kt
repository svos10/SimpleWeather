package com.gmail.segenpro.myweather.data.network.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.network.dto.LocationDto
import com.gmail.segenpro.myweather.domain.core.models.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToLocationMapper @Inject constructor() : Mapper<LocationDto, Location> {

    override fun map(from: LocationDto): Location =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            Location(0,
                    from.name!!,
                    from.region!!,
                    from.country!!,
                    from.latitudeInDegrees!!,
                    from.longitudeInDegrees!!,
                    from.timeZone!!,
                    from.localtimeEpoch!!,
                    from.localtime!!)
}
