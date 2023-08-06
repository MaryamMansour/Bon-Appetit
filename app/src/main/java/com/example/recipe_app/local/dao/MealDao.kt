package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite


@Dao
interface MealDao {

//    @Query("SELECT * FROM FavMeals")
    @Query("SELECT * FROM user_favourite WHERE userID LIKE :userId ")
    suspend fun getFavMeals(userId : String):List<UserFavourite>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMeal(userFavourite: UserFavourite)
    @Query("DELETE FROM user_favourite WHERE userId = :id AND mealId = :mealId")
    suspend fun deleteFavMeal(id : String, mealId: String)

    @Query("SELECT * FROM favmeals")
    suspend fun getFavMealsItem():List<MealX>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavMealItem(MealX: MealX)
    @Query("DELETE FROM favmeals WHERE idMeal = :mealId ")
    suspend fun deleteFavMealItem( mealId: String)
}