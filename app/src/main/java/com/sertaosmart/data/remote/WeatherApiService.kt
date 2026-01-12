package com.sertaosmart.data.remote

import com.sertaosmart.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("location") location: String
    ): WeatherData
}