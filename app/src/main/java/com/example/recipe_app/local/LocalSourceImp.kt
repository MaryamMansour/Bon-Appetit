package com.example.recipe_app.local

import android.content.Context
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo

class LocalSourceImp(val context: Context) : LocalDataSource{
    private  var mealsDao: MealDao
    private  var userDao :PersonInfoDao


    init {
        val DataBase: MealDataBase = MealDataBase.getInstance(context)
        mealsDao = DataBase.mealDao()
        userDao = DataBase.personinfodao()
    }

    override suspend fun getFavMeals(userId : String): List<MealX> = mealsDao.getFavMeals(userId)

    override suspend fun insertFavMeal(meal: MealX) = mealsDao.insertFavMeal(meal)

    override suspend fun deleteFavMeal(id: String) = mealsDao.deleteFavMeal(id)

    override suspend fun getAllPersonInfo(): List<PersonInfo> = userDao.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = userDao.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = userDao.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = userDao.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = userDao.getPersonInfo(email)


}