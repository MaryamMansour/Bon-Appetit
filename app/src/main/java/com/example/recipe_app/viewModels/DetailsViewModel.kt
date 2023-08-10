package com.example.recipe_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (
    private val repository: Repository
)  : ViewModel() {


    private val _singleMeal = MutableLiveData<MealX>()
    val singleMeal: LiveData<MealX> = _singleMeal


    fun getMealById(mealId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _singleMeal.postValue( repository.lookupMealById(mealId).meals[0])
        }
    }

    fun insertFavMealToUser(meal: MealX, userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavMealToUser(meal)
            repository.insertFavMealToUser(UserFavourite(userId, meal.idMeal))
        }
    }

    fun deleteFavMealById(mealId: String, userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavMealById(mealId, userId)
        }
    }


}