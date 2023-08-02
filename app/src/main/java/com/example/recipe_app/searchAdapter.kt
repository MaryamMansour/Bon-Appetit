package com.example.recipe_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.model.MealX
import java.util.Locale

class searchAdapter(var mealList: List<MealX>,
                    var  context: Context,
                    var OnClick : OnClickListener



) : RecyclerView.Adapter<mealAdapter.Holder> () , Filterable {
    val mealListFull = ArrayList<MealX>(mealList)
    class Holder(val row: View) : RecyclerView.ViewHolder(row){


        var textView = row.findViewById<TextView>(R.id.title_text_view)
        var descriptionView = row.findViewById<TextView>(R.id.description_text_view)
        var imageView = row.findViewById<ImageView>(R.id.image_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mealAdapter.Holder {
        val  row=
            LayoutInflater.from(parent.context).inflate(R.layout.simple_row, parent, false )
        return mealAdapter.Holder(row)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: mealAdapter.Holder, position: Int) {

        val meal = mealList[position]
        holder.textView.text=mealList[position].strMeal
//        holder.descriptionView.text=mealList[position].strInstructions
        Glide.with(context).load(mealList[position].strMealThumb).into(holder.imageView)
        holder.itemView.setOnClickListener {
            OnClick.onClick(meal)

        }
        holder.favItem.setOnClickListener{
            OnClick.onFav(holder.favItem,meal)
        }
    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty()){
                    mealList = mealListFull
                }else{
                    val resultList = ArrayList<MealX>()
                    for(row in mealListFull){
                        if(row.strMeal?.lowercase(Locale.getDefault())?.contains(charSearch.lowercase(Locale.getDefault())) == true){
                            resultList.add(row)
                        }
                    }
                    mealList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mealList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mealList = results?.values as ArrayList<MealX>
                notifyDataSetChanged()

            }

        }

    }
    fun setFilteredList(mealList: List<MealX>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

}

