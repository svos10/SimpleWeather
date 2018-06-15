package com.gmail.segenpro.myweather.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ConditionEntity(
        @PrimaryKey
        val code: Int,
        val text: String,
        val icon: String
)
