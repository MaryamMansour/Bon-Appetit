package com.example.recipe_app.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections.addAll

class HomeMealsViewModel (private val repository: Repository)  : ViewModel() {
    private val viewModelScope2 = CoroutineScope(Dispatchers.Main)

    private val _listOfMeals = MutableLiveData<List<MealX>>()
    val listOfMeals: LiveData<List<MealX>> = _listOfMeals


    private val _listOfMealsItems = MutableLiveData<List<MealX>>()
    val listOfMealsItems: MutableLiveData<List<MealX>> = _listOfMealsItems

    private val _listOfMealsSearch = MutableLiveData<List<MealX>>()
    val listOfMealsSearch: MutableLiveData<List<MealX>> = _listOfMealsSearch

    private val _listOfFavMeals = MutableLiveData<List<UserFavourite>>()
    val listOfFavMeals: LiveData<List<UserFavourite>> = _listOfFavMeals


    private val _randomMeal = MutableLiveData<MealX>()
    val randomMeal: LiveData<MealX> = _randomMeal

    fun getRandomMeal() {
        if (randomMeal.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getRandomMeal()
                _randomMeal.postValue(response.meals[0])
            }
        }
    }

    val alphabets = ('a'..'z').map { it.toString() }.shuffled().get(0)


    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMeals.postValue(repository.getMealsResponse(alphabets).meals)
        }
    }
//        fun getFavMeals(userId: String) {
//            viewModelScope.launch  (Dispatchers.IO){
//                _listOfFavMeals.postValue(repository.getFavMeals(userId))
//            }
//
//        }

//        fun inserFavtMeal(meal: UserFavourite) {
//            viewModelScope.launch (Dispatchers.IO){
//                repository.insertFavMeal(meal)
//            }
//        }


    fun deleteFavMeal(mealId: String) {
        viewModelScope.launch {
            repository.deleteFavMealItem(mealId)
        }
    }


    fun getAllFavMeals(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMealsItems.postValue(repository.getFavMealsItem())
            withContext(Dispatchers.Main) {
                _listOfMealsItems.value?.forEach {
                    listOfMeals.value?.forEach { meal ->
                        meal.fav = meal.idMeal == it.idMeal
                    }
                }
            }
        }
    }

    //    fun insertFavMealItem(mealX: MealX) {
//        viewModelScope.launch {
//            repository.insertFavMealItem(mealX)
//
//            var x=_listOfMeals.value?.toMutableList()
//            mealX.fav=true
//            x?.add(mealX)
//            _listOfMeals.value = x!!
//        }
//    }
    fun getFavMealsItem() {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMealsItems.postValue(repository.getFavMealsItem())

        }
    }

    fun getSearchedMeals(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMealsSearch.postValue(repository.getMealsResponse(query).meals ?: listOf())
            withContext(Dispatchers.Main) {
                _listOfMealsSearch.value?.forEach {
                    _listOfMeals.value?.forEach { meal ->
                        it.fav = meal.idMeal == it.idMeal
                    }
                }
            }
        }

    }


    fun update(id: String?, meal: MealX) {
        viewModelScope2.launch {
            withContext(Dispatchers.IO) {

//                meal.userId?.add(id)
                var currentDataList = repository.getuserIDs(meal.idMeal)


                currentDataList?.add(id)

                if (currentDataList != null) {
                    for(i  in 0 until currentDataList.size) {
                        Log.d("Element in add $i ","${currentDataList.get(i)}")

                    }
                }

//                repository.updateEntity(meal)
                repository.updateDataList(meal.idMeal, currentDataList)

                    for(i  in 0 until repository.getuserIDs(meal.idMeal)!!.size) {
                        Log.d("Element in meal  $i ","${repository.getuserIDs(meal.idMeal)!!.get(i)}")

                    }

//             currentDataList   meal.getuserIDs()?.add(id)

                Log.d("MAIL", "$id")
                Log.d("Size Meal", "${repository.getuserIDs(meal.idMeal)?.size}")
                Log.d("Size List ", "${currentDataList?.size}")

                var currentDataList2 = repository.getuserIDs(meal.idMeal)


                if (currentDataList2 != null) {
                    for(i  in 0 until currentDataList2.size) {
                        Log.d("Element in add $i ","${currentDataList2.get(i)}")

                    }
                }
            }


        }
    }


    fun delete(meal: MealX, idToDelete: String) {
        viewModelScope2.launch {
            withContext(Dispatchers.IO){

                var currentIds = repository.getuserIDs(meal.idMeal)
                var emailList : MutableList<String?>? = mutableListOf()
                if (currentIds != null) {


                    Log.d("delete Size List ", "${currentIds?.size}")

                    if (currentIds != null) {
                        // Since we've properly trimmed and split the retrieved string, each email should be separate
                        for (email in currentIds) {
                            if (email != null) {
                                Log.d("Element after", email)
                            }
                        }
                    }
                    if (emailList != null) {
                        for(i  in 0 until emailList.size) {
                            Log.d("Element after ","${emailList.get(i)}")


                        }
                    }

// Convert the list of email strings into a MutableList
                    var index = emailList?.indexOf(idToDelete)
                    Log.d("Element num ","$index")

                }



//                repository.updateDataList(meal.idMeal, currentIds)
//                    meal.userId=currentIds
//                repository.updateEntity(meal)
            }

        }
    }

    fun insertMeal(meal: MealX) {
        viewModelScope.launch {
            repository.insertFavMealItem(meal)
        }
    }


}