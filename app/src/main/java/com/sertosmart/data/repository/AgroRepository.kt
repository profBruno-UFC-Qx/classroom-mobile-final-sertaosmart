package com.sertosmart.data.repository

import com.sertosmart.data.model.QueryHistory
import com.sertosmart.data.model.WeatherData


interface AgroRepository {
    suspend fun getDailyData(stationCode: String): WeatherData
    suspend fun insertQuery(query: QueryHistory)
}