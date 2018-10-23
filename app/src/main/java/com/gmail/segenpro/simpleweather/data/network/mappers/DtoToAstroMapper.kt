package com.gmail.segenpro.simpleweather.data.network.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.network.dto.AstroDto
import com.gmail.segenpro.simpleweather.domain.core.models.Astro
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DtoToAstroMapper @Inject constructor() : Mapper<AstroDto, Astro> {

    override fun map(from: AstroDto): Astro {
        // утверждения !! здесь допустимы, т.к. проверка уже сделана ранее вызовом функции-расширения retrofitResponseToResult()
        var astro = Astro(from.sunrise!!,
                from.sunset!!,
                from.moonrise!!,
                from.moonset!!)

        if (from.moonPhase != null && from.moonIllumination != null) {
            astro = astro.copy(moonPhase = from.moonPhase, moonIllumination = from.moonIllumination)
        }
        return astro
    }
}
