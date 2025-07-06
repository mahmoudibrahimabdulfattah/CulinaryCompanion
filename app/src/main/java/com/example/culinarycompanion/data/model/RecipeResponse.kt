package com.example.culinarycompanion.data.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipes")
    val recipes: List<Recipe> = emptyList(),
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("page")
    val page: String = "1"
)

data class Recipe(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("images")
    val images: List<RecipeImage> = emptyList(),
    @SerializedName("preparationTime")
    val preparationTime: Int? = null,
    @SerializedName("difficultyLevel")
    val difficultyLevel: String? = null,
    @SerializedName("points")
    val points: Int = 0
) {
    val imageUrl: String
        get() = images.find { it.imageType == "MEDIUM" }?.url
            ?: images.firstOrNull()?.url
            ?: ""

    val preparationTimeText: String
        get() = preparationTime?.let { "${it} min" } ?: "N/A"

    val difficultyText: String
        get() = difficultyLevel?.replaceFirstChar { it.uppercase() } ?: "Unknown"
}

data class RecipeImage(
    @SerializedName("url")
    val url: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("width")
    val width: Int = 0,
    @SerializedName("height")
    val height: Int = 0
)