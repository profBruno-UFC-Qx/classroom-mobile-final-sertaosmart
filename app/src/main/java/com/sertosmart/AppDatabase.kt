package com.sertosmart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sertosmart.data.model.QueryHistory // Supondo que a classe da entidade está neste pacote

@Database(entities = [QueryHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun queryHistoryDao(): QueryHistoryDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                // A instância é verificada novamente dentro do synchronized para evitar corridas de threads
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use applicationContext para evitar memory leaks
                    AppDatabase::class.java,
                    "sertaosmart_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                Instance = instance
                // Retorna a nova instância
                instance
            }
        }
    }
}
