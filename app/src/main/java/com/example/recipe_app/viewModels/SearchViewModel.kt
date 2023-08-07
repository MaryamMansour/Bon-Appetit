package com.example.recipe_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(
     val repository: Repository
) : ViewModel()  {

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals

    private val _favMeal = MutableLiveData<List<MealX>>()
    val favMeal: LiveData<List<MealX>> = _favMeal



    fun getMeals(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMeals.postValue(repository.getMealsResponse(query).meals)
        }
    }

    fun getFavMealsByUserId(userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val favMealsId = repository.getFavMealsByUserId(userId)
            val favMeals = repository.getFavMealsByMealsId(favMealsId)
            _favMeal.postValue(favMeals)
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

    fun getMealsWithFavourite(userId: String, query: String){
        viewModelScope.launch {
            val apiMeals = withContext(Dispatchers.IO){
                repository.getMealsResponse(query).meals ?: emptyList()
            }
            val favMeals = withContext(Dispatchers.IO){
                val favMealsId = repository.getFavMealsByUserId(userId)
                repository.getFavMealsByMealsId(favMealsId) ?: emptyList()
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
    fun resetList(){
        _listOfMeals.value = emptyList()
    }

}