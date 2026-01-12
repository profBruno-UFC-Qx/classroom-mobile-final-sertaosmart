package com.sertaosmart.data.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("CHUVA")
    val precipitation: Double = 0.0,

    @SerializedName("EVAPORACAO_PICH")
    val evapotranspiration: Double = 0.0
)