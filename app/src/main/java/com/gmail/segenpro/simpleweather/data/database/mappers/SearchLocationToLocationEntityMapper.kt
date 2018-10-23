package com.gmail.segenpro.simpleweather.data.database.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.entities.LocationEntity
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchLocationToLocationEntityMapper @Inject constructor() : Mapper<SearchLocation, LocationEntity> {

    override fun map(from: SearchLocation): LocationEntity =
            LocationEntity(from.id,
                    from.name.substringBefore(","),
                    from.region,
                    from.country,
                    from.latitudeInDegrees,
                    from.longitudeInDegrees,
                    "")
}
