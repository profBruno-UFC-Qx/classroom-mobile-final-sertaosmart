package com.sertosmart.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sertosmart.data.model.QueryHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface QueryHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(query: QueryHistory)

    // Usamos Flow para que a UI seja atualizada automaticamente quando o banco mudar.
    @Query("SELECT * FROM query_history ORDER BY id DESC")
    fun getAllQueries(): Flow<List<QueryHistory>>
}