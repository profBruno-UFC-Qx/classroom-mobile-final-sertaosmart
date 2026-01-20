package com.sertaosmart.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sertaosmart.data.DAO.CulturaDao
import com.sertaosmart.data.DAO.QueryHistoryDao
import com.sertaosmart.data.model.Cultura
import com.sertaosmart.data.model.QueryHistory

@Database(entities = [QueryHistory::class, Cultura::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun queryHistoryDao(): QueryHistoryDao
    abstract fun culturaDao(): CulturaDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sertaosmart_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                Instance = instance
                instance
            }
        }
    }
}