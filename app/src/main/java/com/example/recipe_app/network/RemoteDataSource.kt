package com.example.recipe_app.network

import com.example.recipe_app.model.Meal
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getMealsResponse(char:String): Meal

    suspend fun getRandomMeal(): Meal

    suspend fun lookupMealById(mealId: String):Meal



}