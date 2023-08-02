package com.example.recipe_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class HomeMealsViewModelFactory (val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeMealsViewModel::class.java)){
            HomeMealsViewModel(context) as T
        }
        else{
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}