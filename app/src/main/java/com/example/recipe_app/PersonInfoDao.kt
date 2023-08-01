package com.example.recipe_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonInfoDao {

    @Query("SELECT * FROM PersonInfo")
    suspend fun getAllPersonInfo(): PersonInfo

    @Update
    suspend fun update(personinfo : PersonInfo)

    @Delete
    suspend fun delete(personinfo: PersonInfo)




}