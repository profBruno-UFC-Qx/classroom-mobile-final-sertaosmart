package com.sertosmart.data.repository

import com.sertosmart.data.model.WeatherData

/**
 * Interface para o repositório que lida com a obtenção de dados agrometeorológicos.
 * Abstrai a origem dos dados (rede, banco de dados, etc.).
 */
interface AgroRepository {
    suspend fun getDailyData(stationCode: String): WeatherData
}