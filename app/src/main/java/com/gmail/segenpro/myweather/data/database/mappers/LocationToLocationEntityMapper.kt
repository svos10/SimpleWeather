package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.LocationEntity
import com.gmail.segenpro.myweather.domain.core.models.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationToLocationEntityMapper @Inject constructor() : Mapper<Location, LocationEntity> {

    override fun map(from: Location): LocationEntity =
            LocationEntity(from.id,
                    from.name,
                    from.region,
                    from.country,
                    from.latitudeInDegrees,
                    from.longitudeInDegrees,
                    from.timeZone)
}
