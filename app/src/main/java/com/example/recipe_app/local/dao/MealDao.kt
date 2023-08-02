package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX


@Dao
interface MealDao {
    @Query("SELECT * FROM FavMeals")
    suspend fun getFavMeals():List<MealX>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMeal(meal : MealX)
    @Delete
    suspend fun deleteFavMeal(meal: MealX)
}