package com.sertosmart.ui.recommendation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertosmart.R
import com.sertosmart.data.local.AppDatabase
import com.sertosmart.data.local.UserPreferencesRepository
import com.sertosmart.data.model.QueryHistory
import com.sertosmart.data.remote.AgroApi // This import might need adjustment if AgroApi moves
import com.sertosmart.data.repository.AgroRepository
import com.sertosmart.data.repository.NetworkAgroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed interface RecommendationUiState {
    data class Success(val recommendation: String) : RecommendationUiState
    data class Error(val message: String) : RecommendationUiState
    object Loading : RecommendationUiState
}

class RecommendationViewModel(
    private val agroRepository: AgroRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var uiState: RecommendationUiState by mutableStateOf(RecommendationUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.selectedStationCode.flatMapLatest { stationCode ->
                getRecommendation(stationCode)
            }.collect {} // Inicia a coleta do Flow
        }
    }

    fun getRecommendation(stationCode: String): Flow<R> {
        viewModelScope.launch {
            uiState = RecommendationUiState.Loading
            try {
                val weatherData = withContext(Dispatchers.IO) {
                    agroRepository.getDailyData(stationCode)
                }

                val irrigationNeeded = weatherData.evapotranspiration - weatherData.precipitation

                val recommendationText = if (irrigationNeeded > 0) {
                    // A conversão para "litros" depende da área da plantação.
                    // Por enquanto, vamos usar o valor em milímetros (mm), que é o padrão.
                    "Irrigue %.1f mm hoje.".format(irrigationNeeded)
                } else {
                    "Não é necessário irrigar hoje. O solo está úmido."
                }

                // Salva no banco de dados
                val historyEntry = QueryHistory(
                    stationCode = stationCode,
                    recommendation = recommendationText,
                    precipitation = weatherData.precipitation,
                    evapotranspiration = weatherData.evapotranspiration,
                    queryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                )
                // A lógica para salvar será adicionada no repositório
                agroRepository.insertQuery(historyEntry)
                uiState = RecommendationUiState.Success(recommendationText)

            } catch (e: Exception) {
                uiState = RecommendationUiState.Error("Não foi possível buscar os dados. Verifique sua conexão.")
            }
        }
        return TODO("Provide the return value")
    }
}

class RecommendationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            val agroService = AgroApi.retrofitService
            val prefsRepository = UserPreferencesRepository(context)
            val dao = AppDatabase.getDatabase(context).queryHistoryDao()
            val repository = NetworkAgroRepository(agroService, dao)
            return RecommendationViewModel(repository, prefsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
