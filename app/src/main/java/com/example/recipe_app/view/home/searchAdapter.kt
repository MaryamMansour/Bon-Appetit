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
import java.util.Locale

class searchAdapter(var mealList: List<MealX>,
                    var  context: Context,
                    var OnClick : OnClickListener

) : RecyclerView.Adapter<searchAdapter.Holder> ()  {
    class Holder(val row: View) : RecyclerView.ViewHolder(row){
        var textView = row.findViewById<TextView>(R.id.title_text_view)
        var descriptionView = row.findViewById<TextView>(R.id.description_text_view)
        var imageView = row.findViewById<ImageView>(R.id.image_view)
        var favItem = row.findViewById<CheckBox>(R.id.fav_box_v1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row= LayoutInflater.from(parent.context).inflate(R.layout.simple_row, parent, false )
        return Holder(row)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: searchAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener(null)
        val meal = mealList[position]
        holder.textView.text=mealList[position].strMeal
        holder.favItem.isChecked = mealList[position].fav
        Glide.with(context).load(mealList[position].strMealThumb).into(holder.imageView)
        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)
        }
        holder.favItem.setOnCheckedChangeListener { _, isChecked ->
            OnClick.onFav(isChecked, meal)
        }
    }


    fun setDataAdapter(mealList: List<MealX>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


}

