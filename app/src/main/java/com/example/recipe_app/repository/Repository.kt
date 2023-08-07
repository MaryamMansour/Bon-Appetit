package com.example.recipe_app.repository

import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite

interface Repository {


    suspend fun getMealsResponse(char:String): Meal

    suspend fun updateEntity(meal: MealX)
    suspend fun getRandomMeal(): Meal

    suspend fun getFavMeals(userId : String):List<MealX>
    suspend fun insertFavMeal(userFavourite: UserFavourite)
    suspend fun deleteFavMeal(id : String, mealId: String)

    suspend fun lookupMealById(mealId: String):Meal


    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo

    suspend fun getFavMealsItem():List<MealX>
    suspend fun insertFavMealItem(mealX: MealX)
    suspend fun deleteFavMealItem( mealId: String)
}