package com.example.culinarycompanion.data.repository

import com.example.culinarycompanion.data.api.RecipeApiService
import com.example.culinarycompanion.data.model.Recipe
import com.example.culinarycompanion.domain.repository.RecipeRepository
import com.example.culinarycompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val apiService: RecipeApiService
) : RecipeRepository {

    override fun getRecipes(page: Int?): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getRecipes(page)
            if (response.isSuccessful) {
                val recipes = response.body()?.recipes ?: emptyList()
                emit(Resource.Success(recipes))
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "Recipes not found"
                    500 -> "Server error. Please try again later"
                    503 -> "Service unavailable. Please try again later"
                    else -> "Error: ${response.code()} - ${response.message()}"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error. Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }
}