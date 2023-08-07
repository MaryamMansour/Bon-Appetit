package com.example.recipe_app.local

import android.content.Context
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite

class LocalSourceImp(val context: Context) : LocalDataSource{
    private  var mealsDao: MealDao
    private  var userDao :PersonInfoDao


    init {
        val DataBase: MealDataBase = MealDataBase.getInstance(context)
        mealsDao = DataBase.mealDao()
        userDao = DataBase.personinfodao()
    }

    override suspend fun getFavMeals(userId : String):List<MealX> = mealsDao.getFavMeals(userId)
    override suspend fun delete()= mealsDao.delete()
    override suspend fun getuserIDs(mealId: String): MutableList<String?>? = mealsDao.getuserIDs(mealId)
    override suspend fun updateDataList(id: String, dataList: MutableList<String?>?) = mealsDao.updateDataList(id, dataList)

//    override suspend fun insertFavMeal(userFavourite: UserFavourite) = mealsDao.insertFavMeal(userFavourite)
//    override suspend fun deleteFavMeal(id : String, mealId: String) = mealsDao.deleteFavMeal(id, mealId)

    override suspend fun getAllPersonInfo(): List<PersonInfo> = userDao.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = userDao.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = userDao.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = userDao.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = userDao.getPersonInfo(email)
    override suspend fun getFavMealsItem(): List<MealX> = mealsDao.getFavMealsItem()

    override suspend fun insertFavMealItem(mealX: MealX) = mealsDao.insertFavMealItem(mealX)

    override suspend fun deleteFavMealItem( mealId: String) = mealsDao.deleteFavMealItem( mealId)


}