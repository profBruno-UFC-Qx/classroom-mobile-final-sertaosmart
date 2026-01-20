package com.sertaosmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sertaosmart.ui.components.*
import com.sertaosmart.ui.cultura.AddEditCulturaScreen
import com.sertaosmart.ui.cultura.CulturaScreen
import com.sertaosmart.ui.cultura.CulturaViewModel
import com.sertaosmart.ui.cultura.CulturaViewModelFactory
import com.sertaosmart.ui.history.HistoryScreen
import com.sertaosmart.ui.history.HistoryViewModelFactory
import com.sertaosmart.ui.recommendation.RecommendationUiState
import com.sertaosmart.ui.recommendation.RecommendationUiState.*
import com.sertaosmart.ui.recommendation.RecommendationViewModel
import com.sertaosmart.ui.recommendation.RecommendationViewModelFactory
import com.sertaosmart.ui.settings.SettingsScreen
import com.sertaosmart.ui.settings.SettingsViewModelFactory
import com.sertaosmart.ui.theme.SertãoSmartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SertaoSmartApp()
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("recommendation", "Início", Icons.Filled.Home)
    object Culturas : Screen("culturas", "Culturas", Icons.Filled.Favorite)
    object History : Screen("history", "Histórico", Icons.Filled.DateRange)
    object Settings : Screen("settings", "Configurações", Icons.Filled.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SertaoSmartApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Culturas,
        Screen.History,
        Screen.Settings
    )

    SertãoSmartTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "Sertão Smart",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ) 
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        bottomNavItems.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = screen.title) },
                                label = { Text(screen.title) },
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            val context = LocalContext.current
            val culturaViewModelFactory = remember { CulturaViewModelFactory(context) }
            val sharedCulturaViewModel: CulturaViewModel = viewModel(factory = culturaViewModelFactory)

            NavHost(
                navController = navController,
                startDestination = "recommendation",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("recommendation") {
                    val factory = remember { RecommendationViewModelFactory(context) }
                    val recommendationViewModel: RecommendationViewModel = viewModel(factory = factory)
                    RecommendationScreen(
                        uiState = recommendationViewModel.uiState,
                        onRefresh = { recommendationViewModel.getRecommendation("A301") },
                        navController = navController
                    )
                }
                composable("history") {
                    val factory = remember { HistoryViewModelFactory(context) }
                    val historyViewModel: com.sertaosmart.ui.history.HistoryViewModel = viewModel(factory = factory)
                    HistoryScreen(historyViewModel = historyViewModel)
                }
                composable("settings") {
                    val factory = remember { SettingsViewModelFactory(context) }
                    val settingsViewModel: com.sertaosmart.ui.settings.SettingsViewModel = viewModel(factory = factory)
                    SettingsScreen(viewModel = settingsViewModel)
                }
                composable("culturas") {
                    CulturaScreen(
                        culturaViewModel = sharedCulturaViewModel,
                        onAddCultura = { navController.navigate("addCultura") },
                        onEditCultura = { navController.navigate("editCultura/$it") }
                    )
                }
                composable("addCultura") {
                    AddEditCulturaScreen(
                        navController = navController,
                        culturaViewModel = sharedCulturaViewModel
                    )
                }
                composable(
                    "editCultura/{culturaId}",
                    arguments = listOf(navArgument("culturaId") { type = NavType.IntType })
                ) { backStackEntry ->
                    AddEditCulturaScreen(
                        navController = navController,
                        culturaViewModel = sharedCulturaViewModel,
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
    onRefresh: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val waterSaved = if (uiState is Success) "%.0f%%".format(uiState.waterSaved) else "--"
    val efficiency = if (uiState is Success) uiState.efficiency else "--"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader(
            title = "Recomendação de Irrigação",
            subtitle = "Baseado em dados agrometeorológicos"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SmartCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                when (uiState) {
                    is Loading -> SmartLoadingIndicator(
                        message = "Buscando dados meteorológicos..."
                    )
                    is Success -> Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (uiState.recommendation.contains("Irrigue")) 
                                Icons.Filled.Warning else Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = if (uiState.recommendation.contains("Irrigue"))
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = uiState.recommendation,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Chuva: %.1f mm | Evap.: %.1f mm".format(
                                uiState.precipitation,
                                uiState.evapotranspiration
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    is Error -> ErrorMessage(
                        message = uiState.message,
                        onRetry = onRefresh
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmartPrimaryButton(
                text = "Atualizar",
                onClick = onRefresh,
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Refresh,
                enabled = uiState !is Loading
            )
            SmartSecondaryButton(
                text = "Histórico",
                onClick = { navController.navigate("history") },
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.DateRange
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                title = "Economia de Água",
                value = waterSaved,
                icon = Icons.Filled.Star,
                modifier = Modifier.weight(1f),
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
            InfoCard(
                title = "Eficiência",
                value = efficiency,
                icon = Icons.Filled.CheckCircle,
                modifier = Modifier.weight(1f),
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}