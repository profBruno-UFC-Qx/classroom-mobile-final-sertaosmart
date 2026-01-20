package com.sertaosmart.ui.cultura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertaosmart.data.model.Cultura
import com.sertaosmart.data.repository.CulturaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CulturaViewModel(private val culturaRepository: CulturaRepository) : ViewModel() {

    val culturas: StateFlow<List<Cultura>> = culturaRepository.getAllCulturas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertCultura(cultura: Cultura) {
        viewModelScope.launch {
            culturaRepository.insert(cultura)
        }
    }

    fun updateCultura(cultura: Cultura) {
        viewModelScope.launch {
            culturaRepository.update(cultura)
        }
    }

    fun deleteCultura(cultura: Cultura) {
        viewModelScope.launch {
            culturaRepository.delete(cultura)
        }
    }
}
