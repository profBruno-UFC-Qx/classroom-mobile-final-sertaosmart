package com.sertaosmart.data.repository

import com.sertaosmart.data.model.QueryHistory
import com.sertaosmart.data.model.WeatherData


interface AgroRepository {
    suspend fun getDailyData(stationCode: String): WeatherData
    suspend fun insertQuery(query: QueryHistory)
}