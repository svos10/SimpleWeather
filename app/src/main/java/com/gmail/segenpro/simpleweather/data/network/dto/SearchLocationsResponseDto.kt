package com.gmail.segenpro.simpleweather.data.network.dto

import com.gmail.segenpro.simpleweather.data.isDtoListValid

class SearchLocationsResponseDto(val searchLocationsDto: List<SearchLocationDto>) : BaseResponseDto {

    override fun isValid(): Boolean = searchLocationsDto.isDtoListValid()
}
