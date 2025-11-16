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
// HistoryViewModelFactory is defined in `HistoryViewModelFactory.kt`.
// Removed duplicate factory here to avoid redeclaration.