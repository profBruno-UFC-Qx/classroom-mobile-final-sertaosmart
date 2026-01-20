package com.sertaosmart.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sertaosmart.data.repository.UserPreferencesRepository
import com.sertaosmart.data.model.Station
import com.sertaosmart.data.remote.AgroApi
import com.sertaosmart.ui.components.SectionHeader
import com.sertaosmart.ui.components.SmartCard
import com.sertaosmart.ui.components.SmartLoadingIndicator
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

    val isDarkTheme: StateFlow<Boolean> = userPreferencesRepository.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

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
                e.printStackTrace()
                val mockStations = listOf(
                    Station(code = "A301", name = "Fortaleza", state = "CE"),
                    Station(code = "A325", name = "Quixeramobim", state = "CE"),
                    Station(code = "A353", name = "Iguatu", state = "CE"),
                    Station(code = "A312", name = "Sobral", state = "CE"),
                    Station(code = "A304", name = "Crateús", state = "CE"),
                    Station(code = "A305", name = "Jaguaruana", state = "CE")
                )
                SettingsUiState.Success(mockStations)
            }
        }
    }

    fun saveStation(stationCode: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveStationCode(stationCode)
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(isDark)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState = viewModel.uiState
    val selectedStationCode by viewModel.selectedStationCode.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader(
            title = "Configurações",
            subtitle = "Personalize o aplicativo"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SmartCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Aparência",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Modo Escuro",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { viewModel.toggleTheme(it) },
                        thumbContent = {
                            if (isDarkTheme) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        }
                    )
                }
            }
        }

        SmartCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Estação Meteorológica",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Selecione a estação mais próxima da sua localização para obter dados precisos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (uiState is SettingsUiState.Success) {
                    val selectedStation = uiState.stations.find { it.code == selectedStationCode }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = "${selectedStation?.name ?: "Selecione"} - ${selectedStation?.state ?: ""}",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            uiState.stations.forEach { station ->
                                DropdownMenuItem(
                                    text = { Text("${station.name} - ${station.state}") },
                                    onClick = {
                                        viewModel.saveStation(station.code)
                                        expanded = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = if (station.code == selectedStationCode)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                )
                            }
                        }
                    }
                } else if (uiState is SettingsUiState.Loading) {
                    SmartLoadingIndicator(message = "Carregando estações...")
                } else {
                    Text(
                        text = "Erro ao carregar estações. Verifique sua conexão.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        SmartCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Sobre o Aplicativo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                InfoRow(label = "Versão", value = "1.0.0")
                InfoRow(label = "Desenvolvido por", value = "Sertão Smart Team")
                InfoRow(label = "Fonte de Dados", value = "INMET")
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}