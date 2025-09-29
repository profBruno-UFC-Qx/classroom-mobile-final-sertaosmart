package com.sertosmart.data.remote

import com.sertosmart.data.model.WeatherData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.inmet.gov.br/" // Exemplo! Use a URL base real da API.

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AgroApiService {
    @GET("estacao/dadosdiarios") // Exemplo! Use o endpoint real.
    suspend fun getDailyData(@Query("codigo") stationCode: String): WeatherData
}

object AgroApi {
    val retrofitService: AgroApiService by lazy { retrofit.create(AgroApiService::class.java) }
}
