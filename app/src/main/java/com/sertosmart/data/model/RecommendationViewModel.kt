package com.sertosmart.ui.recommendation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertosmart.data.remote.AgroApi // This import might need adjustment if AgroApi moves
import com.sertosmart.data.repository.AgroRepository
import com.sertosmart.data.repository.NetworkAgroRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface RecommendationUiState {
    data class Success(val recommendation: String) : RecommendationUiState
    data class Error(val message: String) : RecommendationUiState
    object Loading : RecommendationUiState
}

class RecommendationViewModel(private val agroRepository: AgroRepository) : ViewModel() {

    var uiState: RecommendationUiState by mutableStateOf(RecommendationUiState.Loading)
        private set

    init {
        getRecommendation("A301")
    }

    fun getRecommendation(stationCode: String) {
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

                uiState = RecommendationUiState.Success(recommendationText)

            } catch (e: Exception) {
                uiState = RecommendationUiState.Error("Não foi possível buscar os dados. Verifique sua conexão.")
            }
        }
    }
}

object RecommendationViewModelFactory {
    fun create(): RecommendationViewModel {
        val agroService = AgroApi.retrofitService
        val repository = NetworkAgroRepository(agroService)
        return RecommendationViewModel(repository)
    }
}
