package com.sertaosmart.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa os dados meteorológicos retornados pela API do INMET.
 * Usamos @SerializedName para mapear os nomes do JSON (ex: "CHUVA")
 * para os nomes das nossas propriedades (ex: "precipitation").
 */
data class WeatherData(
    // O campo na API é "CHUVA" (precipitação em mm)
    @SerializedName("CHUVA")
    val precipitation: Double = 0.0,

    // O campo na API é "EVAPORACAO_PICH" (evaporação em mm)
    @SerializedName("EVAPORACAO_PICH")
    val evapotranspiration: Double = 0.0
)