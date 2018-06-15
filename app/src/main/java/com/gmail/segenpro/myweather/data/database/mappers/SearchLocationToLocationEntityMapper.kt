package com.gmail.segenpro.myweather.data.database.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.LocationEntity
import com.gmail.segenpro.myweather.domain.core.models.SearchLocation
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
