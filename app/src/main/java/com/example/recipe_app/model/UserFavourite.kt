package com.example.recipe_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favourite" , primaryKeys = ["userId","mealId"])
 data class UserFavourite (
    val userId: String,
    val mealId: String,
    val isFavourite: Boolean,
    val strArea: String,
    val strCategory: String,
    val strMeal: String?,
    val strMealThumb: String,
    val strYoutube: String,
    val strInstructions: String,
    )