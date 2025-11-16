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
        // Pega a data de hoje no formato AAAA-MM-DD exigido pela API
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        // A API retorna uma lista, mesmo para um único dia.
        // Pegamos o primeiro (e único) resultado.
        val dailyDataList = agroApiService.getDailyData(stationCode, today, today)
        return dailyDataList.firstOrNull()
            ?: throw NoSuchElementException("Nenhum dado retornado pela API para a data de hoje.")
    }

    override suspend fun insertQuery(query: QueryHistory) {
        queryHistoryDao.insert(query)
    }
}