package com.example.recipe_app.utils

import android.content.Context

object CurrentUser {
    fun getCurrentUser(context: Context): String {
         return context.getSharedPreferences("mypref",0).getString("CurrentUserMail","")!!
    }

}