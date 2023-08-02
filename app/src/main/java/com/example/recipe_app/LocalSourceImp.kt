package com.example.recipe_app

import android.content.Context
import com.example.recipe_app.model.MealDao
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.mealDataBase

class LocalSourceImp(val context: Context) {
    private lateinit var dao: MealDao


    init {
        val DataBase: mealDataBase= mealDataBase.getInstance(context)
        dao = DataBase.mealDao()
    }
    suspend fun getFavMeals():List<MealX>{
        return dao.getFavMeals()
    }
    suspend fun insertFavMeal(mealX: MealX){
        dao.insertFavMeal(mealX)
    }

}