package com.example.recipe_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PersonInfo (
    @PrimaryKey(autoGenerate = true)
    var id :Int,
    val email :String,
    val password :String,
        ){
    var name :String ="default"

}