package com.example.recipe_app.network

import android.util.Log
import com.example.recipe_app.model.Meal

object  ApiClient : RemoteDataSource {

     override suspend fun getMealsResponse(char:String): Meal {
        return RetrofitHelper.retrofit.create(ApiInterface::class.java).getMeals(char)
    }
    override suspend fun getRandomMeal(): Meal {
        return RetrofitHelper.retrofit.create(ApiInterface::class.java).getRandomMeal()
    }

}