package com.sertaosmart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "query_history")
data class QueryHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationCode: String,
    val recommendation: String,
    val precipitation: Double,
    val evapotranspiration: Double,
    val queryDate: String
)