package com.sertaosmart.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sertaosmart.data.model.QueryHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface QueryHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(query: QueryHistory)

    @Query("SELECT * FROM query_history ORDER BY id DESC")
    fun getAllQueries(): Flow<List<QueryHistory>>
}