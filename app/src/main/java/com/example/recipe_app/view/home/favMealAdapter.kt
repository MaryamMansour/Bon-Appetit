package com.example.recipe_app.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite


class favMealAdapter(
    var OnClick : OnClickListener
    
) : RecyclerView.Adapter<favMealAdapter.Holder> (){
    var listOfMeals = mutableListOf<MealX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row= LayoutInflater.from(parent.context).inflate(R.layout.fav_row, parent, false )
        return Holder(row)
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentMeal = listOfMeals[position]
        //todo: bind remain data to views
        holder.textView.text=currentMeal.strMeal
        Glide.with(holder.itemView.context).load(currentMeal.strMealThumb).into(holder.imageView)

        holder.itemView.setOnClickListener {
            OnClick.onClick(currentMeal)
        }
    }

    override fun getItemCount(): Int {
        return listOfMeals.size
    }


    class Holder(row: View) : RecyclerView.ViewHolder(row){
        //todo: find remain views and set them
        var textView = row.findViewById<TextView>(R.id.title_text_view_fav)
        var imageView = row.findViewById<ImageView>(R.id.image_view_fav)

    }
    fun setDataAdapter(mealList: List<MealX>){
        listOfMeals = mealList.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(adapterPosition: Int) {
        listOfMeals.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }


}