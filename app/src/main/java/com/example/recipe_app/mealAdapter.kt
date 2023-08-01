package com.example.recipe_app

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.model.MealX


class mealAdapter(


    var mealList: List<MealX>,
    var  context: Context,
    var OnClick : OnClickListener


) : RecyclerView.Adapter<mealAdapter.Holder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val  row=
            LayoutInflater.from(parent.context).inflate(R.layout.simple_row, parent, false )
        return Holder(row)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val meal = mealList[position]
        holder.textView.text=mealList[position].strMeal
//        holder.descriptionView.text=mealList[position].strInstructions
        Glide.with(context).load(mealList[position].strMealThumb).into(holder.imageView)
        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)

        }

    }

    override fun getItemCount(): Int {
        return mealList.size
    }


    class Holder(val row: View) : RecyclerView.ViewHolder(row){


        var textView = row.findViewById<TextView>(R.id.title_text_view)
        var descriptionView = row.findViewById<TextView>(R.id.description_text_view)
        var imageView = row.findViewById<ImageView>(R.id.image_view)

    }

}