package com.example.recipe_app.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo

@Database(entities = [MealX::class, PersonInfo::class], version=4)
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