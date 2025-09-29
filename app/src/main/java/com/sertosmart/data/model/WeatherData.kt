package com.sertosmart.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa os dados meteorológicos recebidos da API.
 * Os nomes das variáveis devem corresponder às chaves no JSON da API.
 * A anotação @SerializedName é útil se os nomes forem diferentes.
 */
data class WeatherData(
    @SerializedName("precipitacao") val precipitation: Double,
    @SerializedName("evapotranspiracao") val evapotranspiration: Double
)