package com.example.culinarycompanion.presentation.mvi

import com.example.culinarycompanion.data.model.Recipe

data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val isEmpty: Boolean = false
) {
    val showError: Boolean get() = error != null && recipes.isEmpty()
    val showEmptyState: Boolean get() = isEmpty && recipes.isEmpty() && !isLoading
}
