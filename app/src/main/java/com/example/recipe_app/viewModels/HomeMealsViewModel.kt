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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class HomeMealsViewModel @Inject constructor(
    private val repository: Repository
)  : ViewModel() {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals

    private val _randomMeal = MutableLiveData<MealX>()
    val randomMeal: LiveData<MealX> = _randomMeal

    private val _favMeal = MutableLiveData<List<MealX>>()
    val favMeal: LiveData<List<MealX>> = _favMeal

    fun getRandomMeal(){
        if(randomMeal.value == null){
        viewModelScope.launch(Dispatchers.IO) {
            val response =  repository.getRandomMeal()
            _randomMeal.postValue(response.meals[0])
        }
    }}

    private val alphabets = ('a'..'z').map { it.toString() }.shuffled()[0]


    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMeals.postValue(repository.getMealsResponse(alphabets).meals)
        }
    }



    fun insertFavMealToUser(meal: MealX, userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavMealToUser(meal)
            repository.insertFavMealToUser(UserFavourite(userId, meal.idMeal))
//            getMealsWithFavourite(userId)
        }
    }

    fun deleteFavMealById(mealId: String, userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavMealById(mealId, userId)
//            repository.deleteFavMealById(mealId)
//            getMealsWithFavourite(userId)

        }
    }


    fun getFavMealsByUserId(userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val favMealsId = repository.getFavMealsByUserId(userId)
            val favMeals = repository.getFavMealsByMealsId(favMealsId)
            _favMeal.postValue(favMeals)
        }
    }


    ////////test////////
    fun getMealsWithFavourite(userId: String){
        viewModelScope.launch {
            val apiMeals = withContext(Dispatchers.IO){
                 repository.getMealsResponse(alphabets).meals
            }
            val favMeals = withContext(Dispatchers.IO){
                val favMealsId = repository.getFavMealsByUserId(userId)
                repository.getFavMealsByMealsId(favMealsId)
            }
            val mealsWithFav = apiMeals.map { meal ->
                favMeals.forEach { favMeal ->
                    if (meal.idMeal == favMeal.idMeal) {
                        meal.isFavourite = true
                    }
                }
                meal
            }
            _listOfMeals.postValue(mealsWithFav)

        }
    }















}

