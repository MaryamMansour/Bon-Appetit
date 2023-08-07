package com.example.recipe_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repository.Repository

class ViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeMealsViewModel::class.java)) {
            HomeMealsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            FavouriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}