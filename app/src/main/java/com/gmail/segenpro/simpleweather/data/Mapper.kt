package com.gmail.segenpro.simpleweather.data

interface Mapper<From, To> {

    fun map(from: From): To

    fun map(fromList: List<From>) = ArrayList<To>().apply {
        fromList.forEach { add(map(it)) }
    }
}
