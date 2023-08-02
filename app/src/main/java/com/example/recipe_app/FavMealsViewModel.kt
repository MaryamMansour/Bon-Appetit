//package com.example.recipe_app
//
//import android.content.Context
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.recipe_app.model.MealX
//import kotlinx.coroutines.launch
//
//class FavMealsViewModel(val context: Context) : ViewModel(){
//
//    val _meals = MutableLiveData<List<MealX>>()
//
//
//    val db = LocalSourceImp(context)
//    fun getMeals(){
//
//        viewModelScope.launch {
//
//            _meals.value= db.getFavMeals()
//        }
//
//    }
//
//    fun insertMeal (meal :MealX)
//    {
//
//        viewModelScope.launch {
//            db.insertFavMeal(meal)
//        }
//    }
////
////    fun deleteMeal (meal :MealX)
////    {
////
////        viewModelScope.launch {
////            db.(meal)
////        }
////    }
//
//
//}