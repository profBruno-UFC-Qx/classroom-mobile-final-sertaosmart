package com.sertosmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sertosmart.ui.cultura.AddEditCulturaScreen
import com.sertosmart.ui.cultura.CulturaScreen
import com.sertosmart.ui.history.HistoryScreen
import com.sertosmart.ui.history.HistoryViewModelFactory
import com.sertosmart.ui.recommendation.RecommendationUiState
import com.sertosmart.ui.recommendation.RecommendationUiState.*
import com.sertosmart.ui.recommendation.RecommendationViewModel
import com.sertosmart.ui.recommendation.RecommendationViewModelFactory
import com.sertosmart.ui.settings.SettingsScreen
import com.sertosmart.ui.settings.SettingsViewModelFactory
import com.sertosmart.ui.theme.SertãoSmartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SertaoSmartApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SertaoSmartApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    SertãoSmartTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sertão Smart") },
                    actions = {
                        IconButton(onClick = { navController.navigate("culturas") }) {
                            Icon(Icons.Filled.Agriculture, contentDescription = "Culturas")
                        }
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Configurações")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "recommendation",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("recommendation") {
                    val factory = RecommendationViewModelFactory(context)
                    val recommendationViewModel: RecommendationViewModel = viewModel(factory = factory)
                    RecommendationScreen(
                        uiState = recommendationViewModel.uiState,
                        navController = navController
                    )
                }
                composable("history") {
                    val factory = HistoryViewModelFactory(context)
                    val historyViewModel: com.sertosmart.ui.history.HistoryViewModel = viewModel(factory = factory)
                    HistoryScreen(historyViewModel = historyViewModel)
                }
                composable("settings") {
                    val factory = SettingsViewModelFactory(context)
                    val settingsViewModel: com.sertosmart.ui.settings.SettingsViewModel = viewModel(factory = factory)
                    SettingsScreen(viewModel = settingsViewModel)
                }
                composable("culturas") {
                    CulturaScreen(
                        onAddCultura = { navController.navigate("addCultura") },
                        onEditCultura = { navController.navigate("editCultura/$it") }
                    )
                }
                composable("addCultura") {
                    AddEditCulturaScreen(navController = navController)
                }
                composable(
                    "editCultura/{culturaId}",
                    arguments = listOf(navArgument("culturaId") { type = NavType.IntType })
                ) { backStackEntry ->
                    AddEditCulturaScreen(
                        navController = navController,
                        culturaId = backStackEntry.arguments?.getInt("culturaId")
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationScreen(
    uiState: RecommendationUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            when (uiState) {
                is Loading -> CircularProgressIndicator()
                is Success -> Text(text = uiState.recommendation, textAlign = TextAlign.Center)
                is Error -> Text(text = uiState.message)
            }
        }
        Button(onClick = { navController.navigate("history") }) {
            Text("Ver Histórico")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SertãoSmartTheme {
    }
}