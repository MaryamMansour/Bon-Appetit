package com.example.recipe_app.view.home

import android.content.Context
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


class favMealAdapter(


    var mealList: List<MealX>,
    var OnClick : OnClickListener



) : RecyclerView.Adapter<favMealAdapter.Holder> (){
    var mealListM = mealList.toMutableList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row=
            LayoutInflater.from(parent.context).inflate(R.layout.fav_row, parent, false )
        return Holder(row)
    }
    fun deleteItem(i: Int){
        mealListM.removeAt(i)
        notifyDataSetChanged()

    }


    override fun onBindViewHolder(holder: Holder, position: Int) {


        val meal = mealListM[position]
        holder.textView.text=mealListM[position].strMeal
//        holder.descriptionView.text=mealList[position].strInstructions
        Glide.with(holder.itemView.context).load(mealListM[position].strMealThumb).into(holder.imageView)
        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)

        }


    }

    override fun getItemCount(): Int {
        return mealListM.size
    }


    class Holder(val row: View) : RecyclerView.ViewHolder(row){


        var textView = row.findViewById<TextView>(R.id.title_text_view_fav)
        var descriptionView = row.findViewById<TextView>(R.id.description_text_view_fav)
        var imageView = row.findViewById<ImageView>(R.id.image_view_fav)


    }


}