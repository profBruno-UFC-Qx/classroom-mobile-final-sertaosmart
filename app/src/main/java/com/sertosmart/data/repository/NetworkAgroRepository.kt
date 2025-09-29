package com.sertosmart.data.repository

import com.sertosmart.data.model.WeatherData
import com.sertosmart.data.remote.AgroApiService

/**
 * Implementação do repositório que busca os dados da rede usando o AgroApiService.
 */
class NetworkAgroRepository(
    private val agroApiService: AgroApiService
) : AgroRepository {
    override suspend fun getDailyData(stationCode: String): WeatherData {
        return agroApiService.getDailyData(stationCode)
    }
}