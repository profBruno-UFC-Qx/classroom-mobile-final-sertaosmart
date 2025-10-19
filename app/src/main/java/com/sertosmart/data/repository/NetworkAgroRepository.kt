package com.sertosmart.data.repository

import com.sertosmart.data.model.WeatherData
import com.sertosmart.data.remote.AgroApiService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Implementação do repositório que busca os dados da rede usando o AgroApiService.
 */
class NetworkAgroRepository(
    private val agroApiService: AgroApiService
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
}