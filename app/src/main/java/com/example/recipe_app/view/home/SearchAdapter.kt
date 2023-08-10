package com.example.recipe_app.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX

class SearchAdapter(
    private var onClick : OnClickListener
) : RecyclerView.Adapter<SearchAdapter.Holder> ()  {
    private var listOfMeals = mutableListOf<MealX>()
    class Holder(row: View) : RecyclerView.ViewHolder(row){
        var textView: TextView = row.findViewById(R.id.title_text_view)
        var imageView: ImageView = row.findViewById(R.id.image_view)
        var favItem: CheckBox = row.findViewById(R.id.fav_box_v1)
        var textArea: TextView = row.findViewById(R.id.area_text_view)
        var textCategory: TextView = row.findViewById(R.id.category_text_view)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row= LayoutInflater.from(parent.context).inflate(R.layout.simple_row, parent, false )
        return Holder(row)
    }

    override fun getItemCount(): Int {
        return listOfMeals.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener(null)
        holder.favItem.setOnCheckedChangeListener(null)
        val meal = listOfMeals[position]
        holder.textView.text=meal.strMeal
        holder.textArea.text=meal.strArea
        holder.textCategory.text=meal.strCategory
        holder.favItem.isChecked = meal.isFavourite
        Glide.with(holder.itemView.context).load(meal.strMealThumb).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onClick.onClick(meal)
        }
        holder.favItem.setOnCheckedChangeListener { _, isChecked ->
            onClick.onFav(isChecked, meal)
        }
    }
    fun setDataAdapter(mealList: List<MealX>){
        listOfMeals= mealList.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(itemId: String) {
        listOfMeals.removeIf { it.idMeal == itemId }
        notifyDataSetChanged()
    }

    fun updateItem(state: Boolean, meal: MealX) {
        listOfMeals.indexOf(meal).let {
            listOfMeals[it].isFavourite = state
            notifyDataSetChanged()
        }
    }





}

