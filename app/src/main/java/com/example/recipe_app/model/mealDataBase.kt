package com.example.recipe_app.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MealX::class], version=2)
abstract class mealDataBase :RoomDatabase() {


    abstract fun mealDao(): MealDao

    companion object{
        @Volatile
        private  var  INSTANCE: mealDataBase?=null
        fun getInstance(context: Context): mealDataBase{
            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    mealDataBase::class.java ,
                    "FavMeals"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }

            }
        }
    }
}