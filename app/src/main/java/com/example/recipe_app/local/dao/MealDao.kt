package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavMealToUser(meal: MealX)

    @Query("SELECT * FROM meal WHERE idMeal IN (:mealId)")
    fun getFavMealsByMealsId(mealId: List<String>): List<MealX>

    @Query("delete from meal where idMeal = :mealId")
    fun deleteFavMealById(mealId: String)






}