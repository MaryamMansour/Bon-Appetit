package com.example.recipe_app.viewModels

import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repository.Repository
import kotlin.coroutines.coroutineContext

class AuthViewModelFactory(val repository: Repository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            AuthViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("NOT FOUND")

        }
    }
}