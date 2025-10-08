package com.sertosmart.data.repository


import com.sertosmart.ApiClient
import com.sertosmart.data.model.WeatherData

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