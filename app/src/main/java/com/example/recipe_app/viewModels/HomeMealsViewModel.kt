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


    private val _listOfMealsItems = MutableLiveData<List<MealX>>()
    val listOfMealsItems: MutableLiveData<List<MealX>> = _listOfMealsItems




    private val _listOfFavMeals = MutableLiveData<List<UserFavourite>>()
    val listOfFavMeals: LiveData<List<UserFavourite>> = _listOfFavMeals


    private val _randomMeal = MutableLiveData<MealX>()
    val randomMeal: LiveData<MealX> = _randomMeal

    fun getRandomMeal(){
        if(randomMeal.value == null){
        viewModelScope.launch {
            val response =  repository.getRandomMeal()
            _randomMeal.value = response.meals[0]
        }
    }}

    val alphabets = ('a'..'z').map { it.toString() }.shuffled().get(0)


    fun getMeals() {
        viewModelScope.launch {
            _listOfMeals.value = repository.getMealsResponse(alphabets).meals
        }
    }
        fun getFavMeals(userId: String) {
            viewModelScope.launch {
                _listOfFavMeals.value = repository.getFavMeals(userId)
                Log.d("favMeals", _listOfFavMeals.value.toString())
            }

        }

        fun inserFavtMeal(meal: UserFavourite) {
            viewModelScope.launch {
                repository.insertFavMeal(meal)
            }
        }

        fun deleteFavMeal(id: String, mealId: String) {
            viewModelScope.launch {
                repository.deleteFavMeal(id, mealId)
                repository.deleteFavMealItem(mealId)
            }
        }

        fun getAllFavMeals(id: String) {
            viewModelScope.launch {
                _listOfFavMeals.value = repository.getFavMeals(id)

                _listOfFavMeals.value?.forEach {
                    val response = repository.lookupMealById(it.mealId).meals[0]
                    repository.insertFavMealItem(response)
                }
                _listOfMealsItems.value = repository.getFavMealsItem()
            }
        }
    fun insertFavMealItem(mealX: MealX) {
        viewModelScope.launch {
            repository.insertFavMealItem(mealX)
        }


    }}
