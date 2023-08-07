package com.example.recipe_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.MealX
import com.example.recipe_app.repository.Repository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavouriteViewModel @Inject constructor(
     val repository: Repository
): ViewModel() {


    private val _favMeal = MutableLiveData<List<MealX>>()
    val favMeal: LiveData<List<MealX>> = _favMeal





    fun getFavMealsByUserId(userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val favMealsId = repository.getFavMealsByUserId(userId)
            val favMeals = repository.getFavMealsByMealsId(favMealsId)
            _favMeal.postValue(favMeals)
        }
    }

    fun deleteFavMealById(mealId: String, userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavMealById(mealId, userId)
//            repository.deleteFavMealById(mealId)
//            getMealsWithFavourite(userId)

        }
    }
}