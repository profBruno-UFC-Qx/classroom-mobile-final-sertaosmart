package com.sertosmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sertosmart.ui.recommendation.RecommendationUiState
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

@Composable
fun SertaoSmartApp(modifier: Modifier = Modifier) {
    SertãoSmartTheme {
        Surface(modifier = modifier.fillMaxSize()) {
            val recommendationViewModel: RecommendationViewModel =
                viewModel(factory = RecommendationViewModelFactory)

            RecommendationScreen(uiState = recommendationViewModel.uiState)
        }
    }
}

@Composable
fun RecommendationScreen(uiState: RecommendationUiState, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is RecommendationUiState.Loading -> CircularProgressIndicator()
            is RecommendationUiState.Success -> Text(text = uiState.recommendation, textAlign = TextAlign.Center)
            is RecommendationUiState.Error -> Text(text = uiState.message)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SertãoSmartTheme {
        RecommendationScreen(uiState = RecommendationUiState.Success("Não é necessário irrigar hoje.\nO solo está úmido."))
    }
}