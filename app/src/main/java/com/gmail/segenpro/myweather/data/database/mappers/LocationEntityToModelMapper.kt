package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.LocationEntity
import com.gmail.segenpro.myweather.domain.core.models.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationEntityToModelMapper @Inject constructor() : Mapper<LocationEntity, Location> {

    override fun map(from: LocationEntity): Location =
            Location(from.id,
                    from.name,
                    from.region,
                    from.country,
                    from.latitudeInDegrees,
                    from.longitudeInDegrees,
                    from.timeZone)
}
