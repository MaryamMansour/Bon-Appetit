package com.example.recipe_app.repository

import com.example.recipe_app.local.LocalDataSource
import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.network.RemoteDataSource

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
)  : Repository {
    override suspend fun getMealsResponse(): Meal = remoteDataSource.getMealsResponse()

    override suspend fun getFavMeals(): List<MealX> = localDataSource.getFavMeals()

    override suspend fun insertFavMeal(meal: MealX) = localDataSource.insertFavMeal(meal)

    override suspend fun deleteFavMeal(meal: MealX) = localDataSource.deleteFavMeal(meal)

    override suspend fun getAllPersonInfo(): List<PersonInfo> = localDataSource.getAllPersonInfo()

    override suspend fun insert(personinfo: PersonInfo) = localDataSource.insert(personinfo)

    override suspend fun update(personinfo: PersonInfo) = localDataSource.update(personinfo)

    override suspend fun delete(personinfo: PersonInfo) = localDataSource.delete(personinfo)

    override suspend fun getPersonInfo(email: String): PersonInfo = localDataSource.getPersonInfo(email)
}
