package com.sertaosmart.data.network

import com.sertaosmart.data.remote.WeatherApiService



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

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