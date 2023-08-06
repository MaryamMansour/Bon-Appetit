package com.example.recipe_app.view.home

import android.widget.CheckBox
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite

interface OnClickListener {
    fun onClick(model: UserFavourite)
    fun onFav(isChecked: Boolean, meal: UserFavourite)

}