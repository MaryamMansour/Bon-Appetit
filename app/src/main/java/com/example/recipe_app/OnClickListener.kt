package com.example.recipe_app

import android.widget.CheckBox
import com.example.recipe_app.model.MealX

interface OnClickListener {
    fun onClick(model: MealX)
    fun onFav(box: CheckBox, meal: MealX)
}