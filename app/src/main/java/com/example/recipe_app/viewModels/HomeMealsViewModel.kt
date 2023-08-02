package com.example.recipe_app.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.launch

class HomeMealsViewModel (private val repository: Repository)  : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals

    private val _listOfFavMeals = MutableLiveData<List<MealX>>()
    val listOfFavMeals: LiveData<List<MealX>> = _listOfFavMeals
    fun getMeals(){
        viewModelScope.launch {
            val response =  repository.getMealsResponse()
            _listOfMeals.value = response.meals

        }
    }

    fun getFavMeals() {
        viewModelScope.launch {
            _listOfFavMeals.value = repository.getFavMeals()
        }
    }

    fun insertMeal (meal :MealX)
    {
        viewModelScope.launch {
            repository.insertFavMeal(meal)
        }
    }



}