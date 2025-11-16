package com.sertosmart.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa uma estação meteorológica da API do INMET.
 * Ex: {"CD_ESTACAO":"A301","DC_NOME":"QUIXADA","SG_ESTADO":"CE", ...}
 */
data class Station(
    @SerializedName("CD_ESTACAO")
    val code: String,
    @SerializedName("DC_NOME")
    val name: String,
    @SerializedName("SG_ESTADO")
    val state: String
)