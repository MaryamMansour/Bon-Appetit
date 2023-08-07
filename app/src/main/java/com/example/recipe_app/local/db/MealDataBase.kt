package com.example.recipe_app.local.db

import android.content.Context
import androidx.room.*
import com.example.recipe_app.local.dao.MealDao
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.RetrofitHelper.gson
import com.google.gson.Gson
import com.google.gson.JsonArray

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@TypeConverters(Converters::class)
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

class Converters {
//    @TypeConverter
//    fun fromJson(json: String): MutableList<String?>? {
//        val typeToken = object : TypeToken<MutableList<String?>?>() {}.type
//        return Gson().fromJson(json, typeToken)
//    }
//
//    @TypeConverter
//    fun toJson(dataList: MutableList<String?>?): String {
//        return Gson().toJson(dataList)
//    }
//private val gson = Gson()
//
//    @TypeConverter
//    fun fromJson(json: String?): MutableList<String?>? {
//        val listType = object : TypeToken<MutableList<String?>?>() {}.type
//        return gson.fromJson(json, listType)
//    }
//
//    @TypeConverter
//    fun toJson(dataList: MutableList<String?>?): String? {
//        return gson.toJson(dataList)
//    }
//@TypeConverter

//fun fromJson(json: String?): MutableList<String?>? {
//    val type = object : TypeToken<MutableList<String?>?>() {}.type
//    return Gson().fromJson(json, type)
//}
//
//    @TypeConverter
//    fun toJson(dataList: MutableList<String?>?): String? {
//        val jsonArray = JsonArray()
//        if (dataList != null) {
//            dataList.forEach { jsonArray.add(it) }
//        }
//        return jsonArray.toString()
//    }


//    @TypeConverter
//    fun fromJson(json: String?): MutableList<String?>? {
//        val listType = object : TypeToken<MutableList<String?>?>() {}.type
//        return gson.fromJson(json, listType)
//    }
//
//    @TypeConverter
//    fun toJson(dataList: MutableList<String?>?): String? {
//        return gson.toJson(dataList)
//    }


    @TypeConverter
    fun fromString(value: String?): MutableList<String>? {
        return value?.split(",")?.map { it.trim() }?.toMutableList()
    }

    @TypeConverter
    fun toString(list: MutableList<String>?): String? {
        return list?.joinToString(",") // Make sure there are no extra spaces after the comma
    }



}
