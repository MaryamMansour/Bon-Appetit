package com.example.recipe_app.network

import android.util.Log
import com.example.recipe_app.model.Meal
import retrofit2.Retrofit
import javax.inject.Inject

class ApiClient @Inject constructor(
      var apiInterface: ApiInterface
): RemoteDataSource {

     override suspend fun getMealsResponse(char:String): Meal {
        return apiInterface.getMeals(char)
    }
    override suspend fun getRandomMeal(): Meal {
        return apiInterface.getRandomMeal()
    }
    override suspend fun lookupMealById(mealId: String): Meal {
        return apiInterface.lookupMealById(mealId)
    }


}