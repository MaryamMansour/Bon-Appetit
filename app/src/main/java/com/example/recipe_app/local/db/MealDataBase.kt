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

//@TypeConverters(Converters::class)
@Database(entities = [UserFavourite::class, PersonInfo::class,MealX::class], version=16)
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
//class Converters {
//    @TypeConverter
//    fun fromStringList(value: String?): MutableList<String>? {
//        return value?.split(",")?.toMutableList()
//    }
//
//    @TypeConverter
//    fun toString(list: MutableList<String>?): String? {
//        return list?.joinToString(",")
//    }
//}

//class Converters{
//@TypeConverter
//fun fromString(value: String?): MutableList<String>? {
//
//    return value?.split(",")?.toMutableList()
////    val listType = object :
////        TypeToken<ArrayList<String?>?>() {}.type
////    return Gson().fromJson(value, listType)
//}
//
//@TypeConverter
//fun fromList(list: MutableList<String?>?): String? {
//    return list?.joinToString(",")
////    val gson = Gson()
////    return gson.toJson(list)
//}
//}