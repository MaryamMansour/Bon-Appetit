package com.example.recipe_app.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeMealsViewModel (private val repository: Repository)  : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals

    private val _randomMeal = MutableLiveData<MealX>()
    val randomMeal: LiveData<MealX> = _randomMeal

    fun getRandomMeal(){
        if(randomMeal.value == null){
        viewModelScope.launch(Dispatchers.IO) {
            val response =  repository.getRandomMeal()
            _randomMeal.postValue(response.meals[0])
        }
    }}

    val alphabets = ('a'..'z').map { it.toString() }.shuffled().get(0)


    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMeals.postValue(repository.getMealsResponse(alphabets).meals)
        }
    }








}

