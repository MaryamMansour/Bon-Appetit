package com.example.recipe_app.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipe_app.model.UserFavourite

@Dao
interface UserFavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertFavMealToUser(userFavourite: UserFavourite)

    @Query("SELECT mealId FROM user_favourite WHERE userId = :userId")
  suspend  fun getFavMealsByUserId(userId: String): List<String>

    @Query("delete from user_favourite where mealId = :mealId and userId = :userId")
   suspend fun deleteFavMealById(mealId: String, userId: String)
}