package com.example.recipe_app.repository

import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo

interface Repository {


    suspend fun getMealsResponse(char:String): Meal
    suspend fun getFavMeals(userId : String):List<MealX>
    suspend fun updateEntity(meal: MealX)
    suspend fun getRandomMeal(): Meal
    suspend fun insertFavMeal(meal : MealX)
    suspend fun deleteFavMeal(id: String)

    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo
}