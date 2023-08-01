package com.example.recipe_app

import androidx.room.*

@Dao
interface PersonInfoDao {

    @Query("SELECT * FROM PersonInfo")
    suspend fun getAllPersonInfo(): List<PersonInfo>

    @Insert
    suspend fun insert(personinfo: PersonInfo)

    @Update
    suspend fun update(personinfo : PersonInfo)

    @Delete
    suspend fun delete(personinfo: PersonInfo)




}