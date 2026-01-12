package com.sertaosmart.data.repository


import com.sertaosmart.data.network.ApiClient
import com.sertaosmart.data.model.WeatherData

class WeatherRepository {

    suspend fun getWeatherData(location: String): WeatherData {
        return ApiClient.weatherApiService.getWeatherData(location)
    }
}