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
    override suspend fun updateEntity(meal: MealX) {

    }

    override suspend fun getRandomMeal(): Meal = remoteDataSource.getRandomMeal()
    override suspend fun getFavMeals(userId: String): List<MealX> = localDataSource.getFavMeals(userId)

    override suspend fun insertFavMeal(userFavourite: UserFavourite) = localDataSource.insertFavMeal(userFavourite)

    override suspend fun deleteFavMeal(id: String, mealId: String) = localDataSource.deleteFavMeal(id, mealId)
    override suspend fun lookupMealById(mealId: String): Meal = remoteDataSource.lookupMealById(mealId)


    override suspend fun getAllPersonInfo(): List<PersonInfo> = localDataSource.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = localDataSource.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = localDataSource.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = localDataSource.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = localDataSource.getPersonInfo(email)
    override suspend fun getFavMealsItem(): List<MealX> = localDataSource.getFavMealsItem()

    override suspend fun insertFavMealItem(mealX: MealX) = localDataSource.insertFavMealItem(mealX)

    override suspend fun deleteFavMealItem(mealId: String) = localDataSource.deleteFavMealItem(mealId)
}
