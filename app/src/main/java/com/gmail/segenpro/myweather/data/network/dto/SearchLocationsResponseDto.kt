package com.gmail.segenpro.myweather.data.network.dto

import com.gmail.segenpro.myweather.data.isDtoListValid

class SearchLocationsResponseDto(val searchLocationsDto: List<SearchLocationDto>) : BaseResponseDto {

    override fun isValid(): Boolean = searchLocationsDto.isDtoListValid()
}
