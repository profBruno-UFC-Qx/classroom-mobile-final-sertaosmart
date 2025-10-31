package com.sertosmart

// File: app/src/main/java/com/sertosmart/data/network/ApiClient.kt

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // !! IMPORTANTE !!
    // Substitua pela URL base da sua API
    private const val BASE_URL = "https://api.exemplo.com/"

    // Cria uma instância do Retrofit configurada
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Expõe a implementação da nossa interface de serviço
    val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}