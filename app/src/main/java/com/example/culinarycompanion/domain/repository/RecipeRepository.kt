package com.example.culinarycompanion.domain.repository

import com.example.culinarycompanion.data.model.Recipe
import com.example.culinarycompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(page: Int? = null): Flow<Resource<List<Recipe>>>
}
