package com.gmail.segenpro.myweather.di

import com.gmail.segenpro.myweather.data.Mapper
import com.gmail.segenpro.myweather.data.database.entities.*
import com.gmail.segenpro.myweather.data.database.mappers.*
import com.gmail.segenpro.myweather.data.database.pojo.HistoryEntitiesWrapper
import com.gmail.segenpro.myweather.data.database.pojo.HistoryObject
import com.gmail.segenpro.myweather.data.network.dto.*
import com.gmail.segenpro.myweather.data.network.mappers.*
import com.gmail.segenpro.myweather.domain.core.models.*
import dagger.Binds
import dagger.Module

@Module
abstract class MappersModule {

    // DTO mappers

    @Binds
    abstract fun bindDtoToSearchLocationMapper(mapper: DtoToSearchLocationMapper): Mapper<SearchLocationDto, SearchLocation>

    @Binds
    abstract fun bindDtoToForecastMapper(mapper: DtoToForecastMapper): Mapper<ForecastResponseDto, Forecast>

    @Binds
    abstract fun bindDtoToHistoryDaysMapper(mapper: DtoToHistoryDaysMapper): Mapper<HistoryResponseDto, HistoryDays>

    @Binds
    abstract fun bindDtoToLocationMapper(mapper: DtoToLocationMapper): Mapper<LocationDto, Location>

    @Binds
    abstract fun bindDtoToAstroMapper(mapper: DtoToAstroMapper): Mapper<AstroDto, Astro>

    @Binds
    abstract fun bindDtoToConditionMapper(mapper: DtoToConditionMapper): Mapper<ConditionDto, Condition>

    @Binds
    abstract fun bindDtoToHourMapper(mapper: DtoToHourMapper): Mapper<HourDto, Hour>

    // Entity mappers

    @Binds
    abstract fun bindModelToAstroEntityMapper(mapper: ModelToAstroEntityMapper): Mapper<Astro, AstroEntity>

    @Binds
    abstract fun bindModelToConditionEntityMapper(mapper: ModelToConditionEntityMapper): Mapper<Condition, ConditionEntity>

    @Binds
    abstract fun bindLocationToLocationEntityMapper(mapper: LocationToLocationEntityMapper): Mapper<Location, LocationEntity>

    @Binds
    abstract fun bindSearchLocationToLocationEntityMapper(mapper: SearchLocationToLocationEntityMapper): Mapper<SearchLocation, LocationEntity>

    @Binds
    abstract fun bindModelToHourEntityMapper(mapper: ModelToHourEntityMapper): Mapper<Hour, HourEntity>

    @Binds
    abstract fun bindModelToHistoryEntityMapper(mapperDay: ModelToHistoryDayEntityMapper): Mapper<HistoryDay, HistoryDayEntity>

    @Binds
    abstract fun bindLocationEntityToModelMapper(mapperDay: LocationEntityToModelMapper): Mapper<LocationEntity, Location>

    // POJO mappers

    @Binds
    abstract fun bindHistoryObjectToModelMapper(mapper: HistoryObjectToModelMapper): Mapper<HistoryObject, HistoryDay>

    @Binds
    abstract fun bindModelToHistoryEntitiesWrapperMapper(mapper: ModelToHistoryEntitiesWrapperMapper): Mapper<HistoryDay, HistoryEntitiesWrapper>
}
