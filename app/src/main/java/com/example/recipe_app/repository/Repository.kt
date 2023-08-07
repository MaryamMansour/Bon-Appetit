package com.example.recipe_app.repository

import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite

interface Repository {


    suspend fun getMealsResponse(char:String): Meal

    suspend fun getRandomMeal(): Meal


    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo

    ////////////////////// user favourite /////////////////////////
    suspend fun insertFavMealToUser(userFavourite: UserFavourite)

    suspend fun getFavMealsByUserId(userId: String): List<String>

    suspend fun deleteFavMealById(mealId: String, userId: String)

    ////////////////////// meal /////////////////////////
    suspend  fun insertFavMealToUser(meal: MealX)
    suspend fun lookupMealById(mealId: String):Meal

    suspend  fun getFavMealsByMealsId(mealId: List<String>): List<MealX>

    suspend fun deleteFavMealById(mealId: String)


}