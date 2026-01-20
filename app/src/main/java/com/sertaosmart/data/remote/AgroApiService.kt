package com.sertaosmart.data.remote

import com.sertaosmart.data.model.Station
import com.sertaosmart.data.model.WeatherData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://apitempo.inmet.gov.br/"

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AgroApiService {
    @GET("estacao/diaria/{dataInicial}/{dataFinal}/{codigoEstacao}")
    suspend fun getDailyData(
        @Path("codigoEstacao") stationCode: String,
        @Path("dataInicial") startDate: String,
        @Path("dataFinal") endDate: String
    ): List<WeatherData>

    @GET("estacoes/A")
    suspend fun getStations(): List<Station>
}

object AgroApi {
    val retrofitService: AgroApiService by lazy { retrofit.create(AgroApiService::class.java) }
}
