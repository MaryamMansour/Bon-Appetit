package com.example.recipe_app.repository

import com.example.recipe_app.local.LocalDataSource
import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.RemoteDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
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


    override suspend fun insertFavMealToUser(userFavourite: UserFavourite) = localDataSource.insertFavMealToUser(userFavourite)

    override suspend fun insertFavMealToUser(meal: MealX) = localDataSource.insertFavMealToUser(meal)

    override suspend fun getFavMealsByUserId(userId: String): List<String> = localDataSource.getFavMealsByUserId(userId)
    override suspend fun deleteFavMealById(mealId: String, userId: String) = localDataSource.deleteFavMealById(mealId, userId)

    override suspend fun deleteFavMealById(mealId: String) = localDataSource.deleteFavMealById(mealId)
    override suspend fun lookupMealById(mealId: String): Meal = remoteDataSource.lookupMealById(mealId)

    override suspend fun getFavMealsByMealsId(mealId: List<String>): List<MealX> = localDataSource.getFavMealsByMealsId(mealId)

}
