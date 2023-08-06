package com.example.recipe_app.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import java.util.Locale

class searchAdapter(var OnClick : OnClickListener
) : RecyclerView.Adapter<searchAdapter.Holder> ()  {
    var listOfMeals = listOf<UserFavourite>()
    class Holder(row: View) : RecyclerView.ViewHolder(row){
        var textView = row.findViewById<TextView>(R.id.title_text_view)
        var imageView = row.findViewById<ImageView>(R.id.image_view)
        var favItem = row.findViewById<CheckBox>(R.id.fav_box_v1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row= LayoutInflater.from(parent.context).inflate(R.layout.simple_row, parent, false )
        return Holder(row)
    }

    override fun getItemCount(): Int {
        return listOfMeals.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val meal = listOfMeals[position]
        holder.textView.text=meal.strMeal
        holder.favItem.isChecked = meal.isFavourite
        Glide.with(holder.itemView.context).load(meal.strMealThumb).into(holder.imageView)

        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)
        }
        holder.favItem.setOnCheckedChangeListener { _, isChecked ->
            OnClick.onFav(isChecked, meal)
        }
    }
    fun setDataAdapter(mealList: List<MealX>){
        //todo: set data to adapter
    }





}

