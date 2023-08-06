package com.example.recipe_app.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite

interface LocalDataSource {


////////////////////  user  /////////////////////////
    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo

////////////////////// user favourite /////////////////////////
    fun insertFavMealToUser(userFavourite: UserFavourite)

    fun getFavMealsByUserId(userId: String): List<String>

    fun deleteFavMealById(mealId: String, userId: String)

    ////////////////////// meal /////////////////////////
    fun insertFavMealToUser(meal: MealX)

    fun getFavMealsByMealsId(mealId: List<String>): List<MealX>

    fun deleteFavMealById(mealId: String)




}