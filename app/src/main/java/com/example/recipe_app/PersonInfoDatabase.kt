package com.example.recipe_app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.model.PersonInfo

@Database(entities = [PersonInfo::class], version = 1)
abstract class PersonInfoDatabase : RoomDatabase() {

abstract fun personinfodao(): PersonInfoDao

companion object {
    @Volatile
    private var INTSTANCE: PersonInfoDatabase? = null

    fun getintstance(context: Context): PersonInfoDatabase {
        return INTSTANCE ?: synchronized(this) {
            INTSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                PersonInfoDatabase::class.java, "personinfo_database"
            )

                .build()

        }


    }

}
}