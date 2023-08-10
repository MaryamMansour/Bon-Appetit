package com.example.recipe_app.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX


class FavMealAdapter(
    private var OnClick : OnClickListener
    
) : RecyclerView.Adapter<FavMealAdapter.Holder> (){
    var listOfMeals = mutableListOf<MealX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row= LayoutInflater.from(parent.context).inflate(R.layout.fav_row, parent, false )
        return Holder(row)
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentMeal = listOfMeals[position]
        //todo: bind remain data to views
        holder.textView.text=currentMeal.strMeal
        holder.textArea.text=currentMeal.strArea
        holder.textCategory.text=currentMeal.strCategory
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
        var textView: TextView = row.findViewById(R.id.title_text_view_fav)
        var imageView: ImageView = row.findViewById(R.id.image_view_fav)
        var textArea: TextView = row.findViewById(R.id.area_text_view_fav)
        var textCategory: TextView = row.findViewById(R.id.category_text_view_fav)

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