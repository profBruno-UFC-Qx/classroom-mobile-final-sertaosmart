package com.sertosmart.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertosmart.data.local.AppDatabase
import com.sertosmart.data.local.QueryHistoryDao
import com.sertosmart.data.model.QueryHistory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Repositório que lida exclusivamente com o acesso ao histórico no banco de dados.
 */
class HistoryRepository(private val queryHistoryDao: QueryHistoryDao) {
    fun getAllQueries() = queryHistoryDao.getAllQueries()
}

/**
 * ViewModel para a tela de Histórico.
 */
class HistoryViewModel(historyRepository: HistoryRepository) : ViewModel() {

    // Expõe o Flow do banco de dados como um StateFlow para a UI observar.
    // O histórico será atualizado automaticamente.
    val historyUiState: StateFlow<List<QueryHistory>> =
        historyRepository.getAllQueries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf()
            )
}

/**
 * Factory para criar o HistoryViewModel com suas dependências (Context).
 */
class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            val dao = AppDatabase.getDatabase(context).queryHistoryDao()
            val repository = HistoryRepository(dao)
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}