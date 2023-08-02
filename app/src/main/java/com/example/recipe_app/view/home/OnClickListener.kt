package com.example.recipe_app.view.home

import android.widget.CheckBox
import com.example.recipe_app.model.MealX

interface OnClickListener {
    fun onClick(model: MealX)
    fun onFav(isChecked: Boolean, meal: MealX)
}