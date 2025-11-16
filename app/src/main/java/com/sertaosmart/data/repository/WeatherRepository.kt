package com.sertaosmart.data.repository


import com.sertaosmart.data.network.ApiClient
import com.sertaosmart.data.model.WeatherData

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