package com.gmail.segenpro.simpleweather.data.database.mappers

import com.gmail.segenpro.simpleweather.data.Mapper
import com.gmail.segenpro.simpleweather.data.database.entities.AstroEntity
import com.gmail.segenpro.simpleweather.domain.core.models.Astro
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelToAstroEntityMapper @Inject constructor() : Mapper<Astro, AstroEntity> {

    override fun map(from: Astro): AstroEntity =
            AstroEntity(0,
                    0,
                    from.sunrise,
                    from.sunset,
                    from.moonrise,
                    from.moonset,
                    from.moonPhase,
                    from.moonIllumination)
}
