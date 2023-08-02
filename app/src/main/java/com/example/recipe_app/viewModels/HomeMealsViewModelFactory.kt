package com.example.recipe_app.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repository.Repository


class HomeMealsViewModelFactory (val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeMealsViewModel::class.java)){
            HomeMealsViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}