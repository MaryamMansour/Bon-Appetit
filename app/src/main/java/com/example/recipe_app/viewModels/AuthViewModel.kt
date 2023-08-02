package com.example.recipe_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel (val repository: Repository): ViewModel()    {
    private val _user = MutableLiveData <PersonInfo> ()
    val user: LiveData<PersonInfo> = _user

    fun getUserByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
        _user.postValue( repository.getPersonInfo(email))
    }
         }

    fun insertUser(personInfo: PersonInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(personInfo)
        }
    }


}