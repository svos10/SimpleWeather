package com.gmail.segenpro.myweather.data.network.mappers

interface Mapper<From, To> {

    fun map(from: From): To

    fun map(fromList: List<From>): List<To> {
        return ArrayList<To>().apply {
            fromList.forEach { add(map(it)) }
        }
    }
}