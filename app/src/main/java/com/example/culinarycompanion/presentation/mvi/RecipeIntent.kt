package com.example.culinarycompanion.presentation.mvi

sealed class RecipeIntent {
    object LoadInitialRecipes : RecipeIntent()
    object LoadMoreRecipes : RecipeIntent()
    object RefreshRecipes : RecipeIntent()
    object RetryLoading : RecipeIntent()
}
