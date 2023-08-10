package com.example.recipe_app.model

import androidx.room.Entity

@Entity(tableName = "user_favourite" , primaryKeys = ["userId","mealId"])
 data class UserFavourite (
    val userId: String,
    val mealId: String,
    )