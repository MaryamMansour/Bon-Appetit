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



class HomeAdapter(private var OnClick : OnClickListener) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private var listOfMeals = mutableListOf<MealX>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_row_v2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener(null)
        holder.favButton.setOnCheckedChangeListener(null)
        val meal = listOfMeals[position]
        holder.title.text = meal.strMeal
        holder.category.text = meal.strCategory
        holder.country.text = meal.strArea
        holder.favButton.isChecked = meal.isFavourite
        Glide.with(holder.itemView.context).load(meal.strMealThumb).into(holder.image)

        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)
        }

        holder.favButton.setOnCheckedChangeListener {_, isChecked ->
            OnClick.onFav(isChecked, meal)

        }
    }

    override fun getItemCount(): Int {
        return listOfMeals.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.img_row_v2)
        var title: TextView = itemView.findViewById(R.id.txt_meal_name_v2)
        var category: TextView = itemView.findViewById(R.id.txt_meal_category_v2)
        var country: TextView = itemView.findViewById(R.id.txt_country_v2)
        var favButton :CheckBox = itemView.findViewById(R.id.fav_box_v2)


    }
    fun setDataToAdapter(newList: List<MealX>){
        listOfMeals= newList.toMutableList()
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