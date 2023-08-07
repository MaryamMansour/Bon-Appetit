package com.example.recipe_app.network

import com.example.recipe_app.model.Meal
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search.php")
    suspend fun getMeals(@Query("s") search : String ):Meal

    @GET("random.php")
    suspend fun getRandomMeal(): Meal

    @GET("lookup.php")
    suspend fun lookupMealById(@Query("i") mealId: String):Meal


}