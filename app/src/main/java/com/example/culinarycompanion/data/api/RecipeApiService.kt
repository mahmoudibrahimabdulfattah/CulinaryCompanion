package com.example.culinarycompanion.data.api

import com.example.culinarycompanion.data.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("v1/guidance/interview/recipe/list")
    suspend fun getRecipes(
        @Query("page") page: Int? = null
    ): Response<RecipeResponse>
}