package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX


@Dao
interface MealDao {

    @Query("SELECT * FROM FavMeals")
    suspend fun getFavMeals():List<MealX>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMeal(meal : MealX)
    @Query("DELETE FROM FavMeals WHERE idMeal = :id")
    suspend fun deleteFavMeal(id : String)
}