package com.gmail.segenpro.myweather.data.network.mappers

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.network.dto.AstroDto
import com.gmail.segenpro.myweather.domain.core.models.Astro
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
