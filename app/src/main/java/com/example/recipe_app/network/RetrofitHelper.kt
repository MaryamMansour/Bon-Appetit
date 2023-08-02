package com.example.recipe_app.network

import com.example.recipe_app.R
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    val gson = GsonBuilder().serializeNulls().create()
    val retrofit = Retrofit.Builder()
        .baseUrl(R.string.BaseUrl.toString())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


}