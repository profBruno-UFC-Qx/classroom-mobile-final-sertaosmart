package com.sertaosmart.data.repository

import com.sertaosmart.data.DAO.QueryHistoryDao
import com.sertaosmart.data.model.QueryHistory
import com.sertaosmart.data.model.WeatherData
import com.sertaosmart.data.remote.AgroApiService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NetworkAgroRepository(
    private val agroApiService: AgroApiService,
    private val queryHistoryDao: QueryHistoryDao
) : AgroRepository {
    override suspend fun getDailyData(stationCode: String): WeatherData {
        val today = LocalDate.now()
        val weekAgo = today.minusDays(7)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        return try {
            val dailyDataList = agroApiService.getDailyData(
                stationCode,
                weekAgo.format(formatter),
                today.format(formatter)
            )
            dailyDataList.lastOrNull()
                ?: createMockWeatherData()
        } catch (e: Exception) {
            createMockWeatherData()
        }
    }

    private fun createMockWeatherData(): WeatherData {
        return WeatherData(
            precipitation = 12.5,
            evapotranspiration = 6.8
        )
    }

    override suspend fun insertQuery(query: QueryHistory) {
        queryHistoryDao.insert(query)
    }
}
