package com.example.recipe_app.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo

interface LocalDataSource {

    suspend fun getFavMeals(userId : String):List<MealX>
    suspend fun updateEntity(meal: MealX)
    suspend fun insertFavMeal(meal : MealX)
    suspend fun deleteFavMeal(id: String)

    suspend fun getAllPersonInfo(): List<PersonInfo>

    suspend fun insert(personinfo: PersonInfo)

    suspend fun update(personinfo : PersonInfo)

    suspend fun delete(personinfo: PersonInfo)

    suspend fun getPersonInfo(email : String) : PersonInfo

}