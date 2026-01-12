package com.sertaosmart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "culturas")
data class Cultura(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
