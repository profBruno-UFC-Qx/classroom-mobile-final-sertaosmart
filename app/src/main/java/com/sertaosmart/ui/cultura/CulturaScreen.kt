package com.sertaosmart.ui.cultura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sertaosmart.data.model.Cultura
import com.sertaosmart.ui.components.EmptyState
import com.sertaosmart.ui.components.SmartCard
import com.sertaosmart.ui.components.SectionHeader

@Composable
fun CulturaScreen(
    culturaViewModel: CulturaViewModel = viewModel(factory = CulturaViewModelFactory(context = LocalContext.current)),
    onAddCultura: () -> Unit,
    onEditCultura: (Int) -> Unit
) {
    val culturas by culturaViewModel.culturas.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCultura,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Cultura")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            SectionHeader(
                title = "Minhas Culturas",
                subtitle = "Gerencie suas plantações"
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (culturas.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Favorite,
                    title = "Nenhuma cultura cadastrada",
                    description = "Adicione suas primeiras culturas para começar a receber recomendações personalizadas",
                    modifier = Modifier.fillMaxSize(),
                    actionButton = {
                        FilledTonalButton(
                            onClick = onAddCultura,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Adicionar Cultura")
                        }
                    }
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(culturas) { cultura ->
                        CulturaItemCard(
                            cultura = cultura,
                            onEdit = { onEditCultura(cultura.id) },
                            onDelete = { culturaViewModel.deleteCultura(cultura) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CulturaItemCard(
    cultura: Cultura,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    SmartCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = cultura.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onEdit,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Cultura")
                }
                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Deletar Cultura")
                }
            }
        }
    }
}