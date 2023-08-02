package com.example.recipe_app.local.dao

import androidx.room.*
import com.example.recipe_app.model.PersonInfo

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

    @Query("SELECT * FROM PersonInfo WHERE email = :email")
    suspend fun getPersonInfo(email : String) : PersonInfo




}