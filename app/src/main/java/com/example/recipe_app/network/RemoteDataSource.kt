package com.example.recipe_app.network

import com.example.recipe_app.model.Meal

interface RemoteDataSource {
    suspend fun getMealsResponse(): Meal
}