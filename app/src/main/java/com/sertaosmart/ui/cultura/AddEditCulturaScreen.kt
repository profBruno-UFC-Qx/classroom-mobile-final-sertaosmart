package com.sertaosmart.ui.cultura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sertaosmart.data.model.Cultura
import com.sertaosmart.ui.components.SectionHeader
import com.sertaosmart.ui.components.SmartCard
import com.sertaosmart.ui.components.SmartPrimaryButton
import com.sertaosmart.ui.components.SmartSecondaryButton
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCulturaScreen(
    navController: NavController,
    culturaViewModel: CulturaViewModel,
    culturaId: Int? = null
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(culturaId) {
        culturaId?.let { id ->
            val cultura = culturaViewModel.culturas.first().find { it.id == id }
            if (cultura != null) {
                name = cultura.name
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (culturaId == null) "Nova Cultura" else "Editar Cultura",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeader(
                title = if (culturaId == null) "Adicionar nova cultura" else "Editar informações",
                subtitle = "Preencha os dados da cultura"
            )

            Spacer(modifier = Modifier.height(8.dp))

            SmartCard {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = if (it.isBlank()) "Nome é obrigatório" else null
                        },
                        label = { Text("Nome da Cultura") },
                        placeholder = { Text("Ex: Milho, Feijão, Mandioca...") },
                        isError = nameError != null,
                        supportingText = nameError?.let { { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    Text(
                        text = "Dica: Cadastre todas as suas culturas para receber recomendações personalizadas de irrigação.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmartSecondaryButton(
                    text = "Cancelar",
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Close
                )
                SmartPrimaryButton(
                    text = if (culturaId == null) "Adicionar" else "Salvar",
                    onClick = {
                        if (name.isBlank()) {
                            nameError = "Nome é obrigatório"
                            return@SmartPrimaryButton
                        }
                        if (culturaId == null) {
                            culturaViewModel.insertCultura(Cultura(name = name))
                        } else {
                            culturaViewModel.updateCultura(Cultura(id = culturaId, name = name))
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Check,
                    enabled = name.isNotBlank()
                )
            }
        }
    }
}
