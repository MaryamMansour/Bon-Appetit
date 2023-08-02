package com.example.recipe_app

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import kotlinx.coroutines.launch

class HomeMealsViewModel (val context: Context)  : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals

    private val _listOfFavMeals = MutableLiveData<List<MealX>>()
    val listOfFavMeals: LiveData<List<MealX>> = _listOfFavMeals




    fun getMeals(){
        viewModelScope.launch {
            val response =  ApiClient.getMealsResponse()
            _listOfMeals.value = response.meals

        }
    }



    val db = LocalSourceImp(context)
    fun getFavMeals(){

        viewModelScope.launch {

            _listOfFavMeals.value= db.getFavMeals()
        }

    }

    fun insertMeal (meal :MealX)
    {

        viewModelScope.launch {

            db.insertFavMeal(meal)
        }
    }



}