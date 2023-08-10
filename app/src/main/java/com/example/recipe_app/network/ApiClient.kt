package com.example.recipe_app.network

import com.example.recipe_app.model.Meal
import javax.inject.Inject

class ApiClient @Inject constructor(
    private var apiInterface: ApiInterface
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