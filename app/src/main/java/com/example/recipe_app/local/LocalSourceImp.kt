package com.example.recipe_app.local

import android.content.Context
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.dao.UserFavouriteDao
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import javax.inject.Inject

class LocalSourceImp @Inject constructor(
       var mealsDao: MealDao,
      var userDao :PersonInfoDao,
     var userFavouriteDao: UserFavouriteDao,
) : LocalDataSource{






    override suspend fun getAllPersonInfo(): List<PersonInfo> = userDao.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = userDao.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = userDao.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = userDao.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = userDao.getPersonInfo(email)
    override suspend fun insertFavMealToUser(userFavourite: UserFavourite) = userFavouriteDao.insertFavMealToUser(userFavourite)

    override suspend fun insertFavMealToUser(meal: MealX) = mealsDao.insertFavMealToUser(meal)

    override suspend fun getFavMealsByUserId(userId: String): List<String> = userFavouriteDao.getFavMealsByUserId(userId)
    override suspend fun deleteFavMealById(mealId: String, userId: String) = userFavouriteDao.deleteFavMealById(mealId, userId)

    override suspend fun deleteFavMealById(mealId: String) = mealsDao.deleteFavMealById(mealId)
    override suspend fun getFavMealsByMealsId(mealId: List<String>): List<MealX> = mealsDao.getFavMealsByMealsId(mealId)


}