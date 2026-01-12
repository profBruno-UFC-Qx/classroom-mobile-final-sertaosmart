package com.sertaosmart.ui.recommendation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertaosmart.data.repository.UserPreferencesRepository
import com.sertaosmart.data.repository.AgroRepository
import com.sertaosmart.data.repository.NetworkAgroRepository
import com.sertaosmart.data.remote.AgroApi
import com.sertaosmart.data.AppDatabase

class RecommendationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            val queryHistoryDao = AppDatabase.getDatabase(context).queryHistoryDao()
            val agroRepository: AgroRepository = NetworkAgroRepository(
                agroApiService = AgroApi.retrofitService,
                queryHistoryDao = queryHistoryDao
            )
            val userPreferencesRepository = UserPreferencesRepository(context)
            @Suppress("UNCHECKED_CAST")
            return RecommendationViewModel(agroRepository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
