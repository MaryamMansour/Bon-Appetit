package com.example.recipe_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repository.Repository

class FavouriteViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            FavouriteViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}