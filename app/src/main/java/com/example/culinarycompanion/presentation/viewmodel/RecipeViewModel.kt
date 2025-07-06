package com.example.culinarycompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarycompanion.domain.repository.RecipeRepository
import com.example.culinarycompanion.domain.util.Resource
import com.example.culinarycompanion.presentation.mvi.RecipeIntent
import com.example.culinarycompanion.presentation.mvi.RecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    private val maxPages = 5

    init {
        processIntent(RecipeIntent.LoadInitialRecipes)
    }

    fun processIntent(intent: RecipeIntent) {
        when (intent) {
            is RecipeIntent.LoadInitialRecipes -> loadInitialRecipes()
            is RecipeIntent.LoadMoreRecipes -> loadMoreRecipes()
            is RecipeIntent.RefreshRecipes -> refreshRecipes()
            is RecipeIntent.RetryLoading -> retryLoading()
        }
    }

    private fun loadInitialRecipes() {
        viewModelScope.launch {
            repository.getRecipes(page = 1).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        val recipes = resource.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            recipes = recipes,
                            isLoading = false,
                            error = null,
                            currentPage = 1,
                            hasMorePages = recipes.isNotEmpty() && 1 < maxPages,
                            isEmpty = recipes.isEmpty()
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreRecipes() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMorePages) return

        val nextPage = currentState.currentPage + 1
        if (nextPage > maxPages) return

        viewModelScope.launch {
            repository.getRecipes(page = nextPage).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoadingMore = true,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        val newRecipes = resource.data ?: emptyList()
                        val allRecipes = _uiState.value.recipes + newRecipes
                        _uiState.value = _uiState.value.copy(
                            recipes = allRecipes,
                            isLoadingMore = false,
                            error = null,
                            currentPage = nextPage,
                            hasMorePages = newRecipes.isNotEmpty() && nextPage < maxPages
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoadingMore = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun refreshRecipes() {
        viewModelScope.launch {
            repository.getRecipes(page = 1).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isRefreshing = true,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        val recipes = resource.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            recipes = recipes,
                            isRefreshing = false,
                            error = null,
                            currentPage = 1,
                            hasMorePages = recipes.isNotEmpty() && 1 < maxPages,
                            isEmpty = recipes.isEmpty()
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isRefreshing = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun retryLoading() {
        val currentState = _uiState.value
        if (currentState.recipes.isEmpty()) {
            loadInitialRecipes()
        } else {
            loadMoreRecipes()
        }
    }
}
