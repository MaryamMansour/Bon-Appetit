package com.example.recipe_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.model.MealX


class FavouriteFragment : Fragment(), OnClickListener {


    lateinit var HomeViewModel: HomeMealsViewModel
    lateinit var favRecyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        getViewModelReady()

        var favRecyclerAdapter : mealAdapter

        HomeViewModel.getFavMeals()



        HomeViewModel.listOfFavMeals.observe(this){ meals->
            Log.d("meal", "HIIII")
            print("HERE")


            favRecyclerView = view.findViewById(R.id.FavRecyclerView)
            favRecyclerAdapter = mealAdapter(meals,requireActivity(), this)
            favRecyclerView.adapter = favRecyclerAdapter
            favRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)



        }





    }

    override fun onClick(model: MealX) {
        Toast.makeText(requireActivity(),"Meal Clicked", Toast.LENGTH_SHORT).show()

    }

    override fun onFav(box: CheckBox, meal: MealX) {

        box.setOnCheckedChangeListener { box , isChecked ->
            if (isChecked)
            {
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
//                HomeViewModel.insertMeal(meal)
            }
            else
            {
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(
            requireActivity()
        )
        HomeViewModel= ViewModelProvider(this,mealsFactory).get(HomeMealsViewModel::class.java)
    }

}