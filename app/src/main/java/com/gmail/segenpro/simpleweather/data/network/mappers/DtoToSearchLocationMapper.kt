package com.gmail.segenpro.simpleweather.data.network.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.network.dto.SearchLocationDto
import com.gmail.segenpro.simpleweather.domain.core.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToSearchLocationMapper @Inject constructor() : Mapper<SearchLocationDto, SearchLocation> {

    override fun map(from: SearchLocationDto): SearchLocation =
    // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
            SearchLocation(from.id!!,
                    from.name!!.substringBefore(","),
                    from.region!!,
                    from.country!!,
                    from.latitudeInDegrees!!,
                    from.longitudeInDegrees!!,
                    from.url!!)
}
