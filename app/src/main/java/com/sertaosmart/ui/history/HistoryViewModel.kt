package com.sertaosmart.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertaosmart.data.AppDatabase
import com.sertaosmart.data.DAO.QueryHistoryDao
import com.sertaosmart.data.model.QueryHistory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HistoryRepository(private val queryHistoryDao: QueryHistoryDao) {
    fun getAllQueries() = queryHistoryDao.getAllQueries()
}

class HistoryViewModel(historyRepository: HistoryRepository) : ViewModel() {

    val historyUiState: StateFlow<List<QueryHistory>> =
        historyRepository.getAllQueries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf()
            )
}