package com.sertosmart

// File: app/src/main/java/com/sertosmart/ui/recommendation/RecommendationViewModel.kt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertosmart.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Define os possíveis estados da UI
sealed interface RecommendationUiState {
    data class Success(val recommendation: String) : RecommendationUiState
    data class Error(val message: String) : RecommendationUiState
    object Loading : RecommendationUiState
}

class RecommendationViewModel : ViewModel() {

    // Estado da UI, observável pelo Composable
    var uiState: RecommendationUiState by mutableStateOf(RecommendationUiState.Loading)
        private set

    private val weatherRepository = WeatherRepository()

    init {
        // Inicia a busca pelos dados assim que o ViewModel é criado
        fetchRecommendation("Quixada-CE")
    }

    private fun fetchRecommendation(location: String) {
        // Inicia a coroutine no escopo do ViewModel
        viewModelScope.launch {
            uiState = RecommendationUiState.Loading
            try {
                val weatherData = weatherRepository.getWeatherData(location)

                // LÓGICA DE RECOMENDAÇÃO (SIMPLIFICADA)
                // Evapotranspiração - Precipitação = Déficit Hídrico
                val waterDeficit = weatherData.evapotranspiration - weatherData.precipitation

                val recommendation = if (waterDeficit > 0) {
                    // Exemplo: recomendar 5 litros para cada mm de déficit
                    // Esta fórmula é um exemplo e deve ser aprimorada!
                    val irrigationAmount = waterDeficit * 5
                    String.format("Irrigue %.1f litros hoje.", irrigationAmount)
                } else {
                    "Não é necessário irrigar hoje."
                }

                uiState = RecommendationUiState.Success(recommendation)

            } catch (e: IOException) {
                // Erro de rede (sem internet, etc)
                uiState = RecommendationUiState.Error("Falha na conexão. Verifique sua internet.")
            } catch (e: Exception) {
                // Outros erros (ex: JSON mal formatado)
                uiState = RecommendationUiState.Error("Ocorreu um erro ao buscar os dados.")
            }
        }
    }
}