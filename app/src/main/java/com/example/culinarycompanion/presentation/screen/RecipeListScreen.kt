package com.example.culinarycompanion.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.culinarycompanion.presentation.components.*
import com.example.culinarycompanion.presentation.mvi.RecipeIntent
import com.example.culinarycompanion.presentation.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.recipes.size - 2 &&
                    uiState.hasMorePages &&
                    !uiState.isLoadingMore &&
                    !uiState.isLoading
                ) {
                    viewModel.processIntent(RecipeIntent.LoadMoreRecipes)
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "The Culinary Companion",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "${uiState.recipes.size} recipes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick = { viewModel.processIntent(RecipeIntent.RefreshRecipes) }
                    ) {
                        if (uiState.isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh recipes",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.processIntent(RecipeIntent.RefreshRecipes) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.showError -> {
                    ErrorItem(
                        message = uiState.error ?: "Unknown error occurred",
                        onRetry = { viewModel.processIntent(RecipeIntent.RetryLoading) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                uiState.showEmptyState -> {
                    EmptyState(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        if (uiState.isLoading && uiState.recipes.isEmpty()) {
                            item {
                                LoadingItem()
                            }
                        } else {
                            items(
                                items = uiState.recipes,
                                key = { it.id }
                            ) { recipe ->
                                RecipeCard(recipe = recipe)
                            }
                        }

                        if (uiState.isLoadingMore) {
                            item {
                                LoadingItem()
                            }
                        }

                        if (uiState.error != null && uiState.recipes.isNotEmpty()) {
                            item {
                                ErrorItem(
                                    message = uiState.error ?: "Unknown error",
                                    onRetry = { viewModel.processIntent(RecipeIntent.RetryLoading) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}