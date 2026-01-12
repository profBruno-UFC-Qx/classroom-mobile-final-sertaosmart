package com.sertaosmart.data.model

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("CD_ESTACAO")
    val code: String,
    @SerializedName("DC_NOME")
    val name: String,
    @SerializedName("SG_ESTADO")
    val state: String
)