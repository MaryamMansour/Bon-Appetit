package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite


@Dao
interface MealDao {

//    @Query("SELECT * FROM FavMeals")
    @Query("SELECT * FROM FavMeals WHERE userId LIKE '%' || :userId || '%'")
    suspend fun getFavMeals(userId : String):List<MealX>
    @Query("UPDATE FavMeals SET userId = :dataList WHERE idMeal = :id")
    fun updateDataList(id: String, dataList: MutableList<String?>?)
    @Query("SELECT userId FROM FavMeals WHERE idMeal = :mealId")
    fun getuserIDs(mealId: String): MutableList<String?>?

    @Query("DELETE FROM FavMeals")
    fun delete()



    @Query("SELECT * FROM favmeals")
    suspend fun getFavMealsItem():List<MealX>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavMealItem(MealX: MealX)
    @Query("DELETE FROM favmeals WHERE idMeal = :mealId ")
    suspend fun deleteFavMealItem( mealId: String)


}