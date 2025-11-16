package com.sertaosmart.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertaosmart.data.repository.UserPreferencesRepository
import com.sertaosmart.data.model.Station
import com.sertaosmart.data.remote.AgroApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SettingsUiState {
    data class Success(val stations: List<Station>) : SettingsUiState
    object Loading : SettingsUiState
    object Error : SettingsUiState
}

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    var uiState: SettingsUiState by mutableStateOf(SettingsUiState.Loading)
        private set

    val selectedStationCode: StateFlow<String> = userPreferencesRepository.selectedStationCode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "A301")

    init {
        getStations()
    }

    private fun getStations() {
        viewModelScope.launch {
            uiState = SettingsUiState.Loading
            uiState = try {
                val stations = AgroApi.retrofitService.getStations()
                SettingsUiState.Success(stations)
            } catch (e: Exception) {
                SettingsUiState.Error
            }
        }
    }

    fun saveStation(stationCode: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveStationCode(stationCode)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState = viewModel.uiState
    val selectedStationCode by viewModel.selectedStationCode.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("Selecione a estação meteorológica:")
        Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            if (uiState is SettingsUiState.Success) {
                val selectedStation = uiState.stations.find { it.code == selectedStationCode }
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        value = "${selectedStation?.name ?: "Selecione"} - ${selectedStation?.state ?: ""}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        uiState.stations.forEach { station ->
                            DropdownMenuItem(
                                text = { Text("${station.name} - ${station.state}") },
                                onClick = {
                                    viewModel.saveStation(station.code)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}