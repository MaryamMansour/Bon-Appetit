package com.example.recipe_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.Meal
import com.example.recipe_app.model.MealX
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.launch

class DetailsViewModel (private val repository: Repository)  : ViewModel() {

//    var _detailsMeal : MutableLiveData<MealX>?=null
//   // val detailsMeal: MutableLiveData<MealX>? = _detailsMeal
//
//
//    fun getDetails(): MutableLiveData<MealX> {
//        viewModelScope.launch {
//            if (_detailsMeal == null)
//                _detailsMeal = MutableLiveData()
//            val response = repository.getMealsResponse().meals
//            _detailsMeal!!.value = response
//        }
//        return _detailsMeal!!
//    }


    private var _detailsMeal = MutableLiveData<List<MealX>>()
    val detailsMeal: LiveData<List<MealX>> = _detailsMeal

    fun getDetails() {
        viewModelScope.launch {

            val response = repository.getMealsResponse("c").meals
            _detailsMeal?.value = response

        }
    }

}