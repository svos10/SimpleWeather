package com.gmail.segenpro.myweather.data.network.dto

import com.gmail.segenpro.myweather.data.isDtoListValid

class SearchLocationsResponseDto : ArrayList<SearchLocationDto>(), BaseResponseDto {

    override fun isValid(): Boolean = isDtoListValid()
}
