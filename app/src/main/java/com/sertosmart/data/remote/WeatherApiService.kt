package com.sertosmart

// File: app/src/main/java/com/sertosmart/data/network/WeatherApiService.kt

import com.sertosmart.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface que define os endpoints da API de dados meteorológicos.
 */
interface WeatherApiService {

    /**
     * Busca os dados de precipitação e evapotranspiração.
     * @param location A localização para a qual buscar os dados (ex: "Quixada-CE").
     *
     * A anotação @GET define que esta é uma requisição HTTP GET.
     * O valor "/weather" é o caminho do endpoint na API.
     * A anotação @Query adiciona "location=Quixada-CE" à URL da requisição.
     * Exemplo de URL final: https://sua-api.com/weather?location=Quixada-CE
     */
    @GET("weather")
    suspend fun getWeatherData(
        @Query("location") location: String
    ): WeatherData
}