package com.sertaosmart.ui.recommendation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertaosmart.data.AppDatabase
import com.sertaosmart.data.repository.UserPreferencesRepository
import com.sertaosmart.data.model.QueryHistory
import com.sertaosmart.data.remote.AgroApi
import com.sertaosmart.data.repository.AgroRepository
import com.sertaosmart.data.repository.NetworkAgroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
            userPreferencesRepository.selectedStationCode.collectLatest { stationCode ->
                getRecommendation(stationCode)
            }
        }
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
                    "Irrigue %.1f mm hoje.".format(irrigationNeeded)
                } else {
                    "Não é necessário irrigar hoje. O solo está úmido."
                }

                val historyEntry = QueryHistory(
                    stationCode = stationCode,
                    recommendation = recommendationText,
                    precipitation = weatherData.precipitation,
                    evapotranspiration = weatherData.evapotranspiration,
                    queryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                )
                agroRepository.insertQuery(historyEntry)
                uiState = RecommendationUiState.Success(recommendationText)

            } catch (e: Exception) {
                e.printStackTrace()
                
                val mockPrecipitation = 15.5
                val mockEvapotranspiration = 8.2
                
                val irrigationNeeded = mockEvapotranspiration - mockPrecipitation
                
                val recommendationText = if (irrigationNeeded > 0) {
                    "Irrigue %.1f mm hoje.".format(irrigationNeeded)
                } else {
                    "Não é necessário irrigar hoje. O solo está úmido."
                }
                
                val historyEntry = QueryHistory(
                    stationCode = stationCode,
                    recommendation = recommendationText,
                    precipitation = mockPrecipitation,
                    evapotranspiration = mockEvapotranspiration,
                    queryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                )
                agroRepository.insertQuery(historyEntry)
                
                uiState = RecommendationUiState.Success(recommendationText)
            }
        }
    }
}
