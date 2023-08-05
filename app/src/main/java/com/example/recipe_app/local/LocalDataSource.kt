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

    suspend fun getFavMeals(userId : String):List<UserFavourite>
    suspend fun insertFavMeal(userFavourite: UserFavourite)
    suspend fun deleteFavMeal(id : String, mealId: String)

    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo

    suspend fun getFavMealsItem():List<MealX>
    suspend fun insertFavMealItem(mealX: MealX)
    suspend fun deleteFavMealItem( mealId: String)

}