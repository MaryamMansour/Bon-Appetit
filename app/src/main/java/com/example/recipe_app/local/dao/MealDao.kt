package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavMealToUser(meal: UserFavourite)
}