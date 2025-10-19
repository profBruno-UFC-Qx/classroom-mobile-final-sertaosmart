package com.sertosmart.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sertosmart.data.model.QueryHistory

@Composable
fun HistoryScreen(
    // O ViewModel passará o estado da UI para cá
    // uiState: HistoryUiState,
    modifier: Modifier = Modifier
) {
    // Por enquanto, vamos usar dados de exemplo
    val fakeHistory = listOf(
        QueryHistory(1, "A301", "Irrigue 5.2 mm hoje.", 0.0, 5.2, "20/05/2024 10:30"),
        QueryHistory(2, "A301", "Não é necessário irrigar hoje.", 10.0, 4.5, "19/05/2024 09:15"),
        QueryHistory(3, "A301", "Irrigue 3.0 mm hoje.", 1.0, 4.0, "18/05/2024 11:00")
    )

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(fakeHistory) { historyItem ->
            HistoryItemCard(historyItem = historyItem)
        }
    }
}

@Composable
fun HistoryItemCard(historyItem: QueryHistory, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Consulta de ${historyItem.queryDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = historyItem.recommendation,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Estação: ${historyItem.stationCode} | Chuva: ${historyItem.precipitation}mm | Evaporação: ${historyItem.evapotranspiration}mm",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}