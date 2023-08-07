package com.example.recipe_app.local.db

import android.content.Context
import androidx.room.*
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.dao.UserFavouriteDao
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Database(entities = [UserFavourite::class, PersonInfo::class,MealX::class], version=1 , exportSchema = false)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun personinfodao(): PersonInfoDao

    abstract fun userFavouriteDao(): UserFavouriteDao


    companion object{
        @Volatile
        private  var  INSTANCE: MealDataBase?=null
        fun getInstance(context: Context): MealDataBase {
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    MealDataBase::class.java ,
                    "FavMeals"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
