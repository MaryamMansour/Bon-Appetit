package com.example.recipe_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repository.Repository

class SearchViewModelFactory(val repository: Repository) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            SearchViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}