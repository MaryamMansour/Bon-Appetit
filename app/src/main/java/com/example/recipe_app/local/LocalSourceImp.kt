package com.example.recipe_app.local

import android.content.Context
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.dao.UserFavouriteDao
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite

class LocalSourceImp(val context: Context) : LocalDataSource{
    private  var mealsDao: MealDao
    private  var userDao :PersonInfoDao
    private var userFavouriteDao: UserFavouriteDao


    init {
        val DataBase: MealDataBase = MealDataBase.getInstance(context)
        mealsDao = DataBase.mealDao()
        userDao = DataBase.personinfodao()
        userFavouriteDao = DataBase.userFavouriteDao()
    }



    override suspend fun getAllPersonInfo(): List<PersonInfo> = userDao.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = userDao.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = userDao.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = userDao.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = userDao.getPersonInfo(email)
    override fun insertFavMealToUser(userFavourite: UserFavourite) = userFavouriteDao.insertFavMealToUser(userFavourite)

    override fun insertFavMealToUser(meal: MealX) = mealsDao.insertFavMealToUser(meal)

    override fun getFavMealsByUserId(userId: String): List<String> = userFavouriteDao.getFavMealsByUserId(userId)
    override fun deleteFavMealById(mealId: String, userId: String) = userFavouriteDao.deleteFavMealById(mealId, userId)

    override fun deleteFavMealById(mealId: String) = mealsDao.deleteFavMealById(mealId)
    override fun getFavMealsByMealsId(mealId: List<String>): List<MealX> = mealsDao.getFavMealsByMealsId(mealId)


}