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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sertosmart.ui.recommendation.RecommendationUiState
import com.sertosmart.ui.history.HistoryScreen
import com.sertosmart.ui.recommendation.RecommendationUiState.*
import com.sertosmart.ui.recommendation.RecommendationViewModel
import com.sertosmart.ui.recommendation.RecommendationViewModelFactory
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
    val navController = rememberNavController()
    SertãoSmartTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Sertão Smart") })
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "recommendation",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("recommendation") {
                    val recommendationViewModel: RecommendationViewModel =
                        viewModel(factory = RecommendationViewModelFactory)
                    RecommendationScreen(
                        uiState = recommendationViewModel.uiState,
                        navController = navController
                    )
                }
                composable("history") {
                    HistoryScreen()
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