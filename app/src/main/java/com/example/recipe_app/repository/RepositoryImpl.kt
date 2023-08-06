package com.example.recipe_app.repository

import com.example.recipe_app.local.LocalDataSource
import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.RemoteDataSource

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
)  : Repository {
    override suspend fun getMealsResponse(char:String): Meal = remoteDataSource.getMealsResponse(char)


    override suspend fun getRandomMeal(): Meal = remoteDataSource.getRandomMeal()



    override suspend fun getAllPersonInfo(): List<PersonInfo> = localDataSource.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = localDataSource.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = localDataSource.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = localDataSource.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = localDataSource.getPersonInfo(email)


    override fun insertFavMealToUser(userFavourite: UserFavourite) = localDataSource.insertFavMealToUser(userFavourite)

    override fun insertFavMealToUser(meal: MealX) = localDataSource.insertFavMealToUser(meal)

    override fun getFavMealsByUserId(userId: String): List<String> = localDataSource.getFavMealsByUserId(userId)
    override fun deleteFavMealById(mealId: String, userId: String) = localDataSource.deleteFavMealById(mealId, userId)

    override fun deleteFavMealById(mealId: String) = localDataSource.deleteFavMealById(mealId)
    override fun getFavMealsByMealsId(mealId: List<String>): List<MealX> = localDataSource.getFavMealsByMealsId(mealId)

}
