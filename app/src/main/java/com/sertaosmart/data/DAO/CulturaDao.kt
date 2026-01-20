package com.sertaosmart.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sertaosmart.data.model.Cultura
import kotlinx.coroutines.flow.Flow

@Dao
interface CulturaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cultura: Cultura)

    @Update
    suspend fun update(cultura: Cultura)

    @Delete
    suspend fun delete(cultura: Cultura)

    @Query("SELECT * from culturas ORDER BY name ASC")
    fun getAllCulturas(): Flow<List<Cultura>>

    @Query("SELECT * from culturas WHERE id = :id")
    fun getCultura(id: Int): Flow<Cultura>
}
