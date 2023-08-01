package com.example.recipe_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import kotlinx.coroutines.launch

class HomeMealsViewModel : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals



    fun getMeals(){
        viewModelScope.launch {
            val response =  ApiClient.getMealsResponse()
            _listOfMeals.value = response.meals

        }
    }



}