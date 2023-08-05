package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX


@TypeConverters
@Dao
interface MealDao {

//    @Query("SELECT * FROM FavMeals")
    @Query("SELECT * FROM FavMeals WHERE userId LIKE '%' || :userId || '%'")
    suspend fun getFavMeals(userId : String):List<MealX>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMeal(meal : MealX)
    @Query("DELETE FROM FavMeals WHERE idMeal = :id")
    suspend fun deleteFavMeal(id : String)
    @Update
    fun updateEntity(meal: MealX)
}