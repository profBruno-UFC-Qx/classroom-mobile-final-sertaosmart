package com.sertosmart.data.remote

import com.sertosmart.data.model.WeatherData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.GET

private const val BASE_URL = "https://apitempo.inmet.gov.br/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Interface para a API do INMET.
 */
interface AgroApiService {
    /**
     * Busca os dados diários de uma estação para um intervalo de datas.
     * A API do INMET retorna uma lista, mesmo que seja para um único dia.
     */
    @GET("estacao/diaria/{dataInicial}/{dataFinal}/{codigoEstacao}")
    suspend fun getDailyData(
        @Path("codigoEstacao") stationCode: String,
        @Path("dataInicial") startDate: String,
        @Path("dataFinal") endDate: String
    ): List<WeatherData> // A API retorna uma lista de dados diários
}

object AgroApi {
    val retrofitService: AgroApiService by lazy { retrofit.create(AgroApiService::class.java) }
}
