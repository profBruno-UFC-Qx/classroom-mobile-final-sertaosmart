package com.sertaosmart.ui.cultura

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sertaosmart.data.model.Cultura
import kotlinx.coroutines.flow.first

@Composable
fun AddEditCulturaScreen(
    navController: NavController,
    culturaViewModel: CulturaViewModel = viewModel(factory = CulturaViewModelFactory(context = LocalContext.current)),
    culturaId: Int? = null
) {
    var name by remember { mutableStateOf("") }

    LaunchedEffect(culturaId) {
        culturaId?.let { id ->
            val cultura = culturaViewModel.culturas.first().find { it.id == id }
            if (cultura != null) {
                name = cultura.name
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome da Cultura") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (culturaId == null) {
                        culturaViewModel.insertCultura(Cultura(name = name))
                    } else {
                        culturaViewModel.updateCultura(Cultura(id = culturaId, name = name))
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (culturaId == null) "Adicionar" else "Salvar")
            }
        }
    }
}
