package com.example.recipe_app.local.db

import android.content.Context
import androidx.room.*
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Database(entities = [UserFavourite::class, PersonInfo::class], version=17)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun personinfodao(): PersonInfoDao


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
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
