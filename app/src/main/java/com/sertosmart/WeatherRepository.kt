package com.sertosmart

// File: app/src/main/java/com/sertosmart/data/repository/WeatherRepository.kt

import com.sertosmart.data.model.WeatherData
import com.sertosmart.data.network.ApiClient

class WeatherRepository {

    /**
     * Busca os dados meteorológicos da API.
     * Em um app real, aqui poderia ter lógica para cache ou fallback.
     */
    suspend fun getWeatherData(location: String): WeatherData {
        // Chama a função suspensa do nosso ApiService
        return ApiClient.weatherApiService.getWeatherData(location)
    }
}