package com.example.recipe_app

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.model.MealX
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay


class HomeFragment : Fragment(), OnClickListener {

    lateinit var viewModel: HomeMealsViewModel


    lateinit var recyclerView: RecyclerView
    lateinit var favouriteBox: CheckBox


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        viewModel = ViewModelProvider(this).get(HomeMealsViewModel::class.java)
        viewModel.getMeals()

        var recyclerAdapter : mealAdapter




        viewModel.listOfMeals.observe(this){ meals->


            recyclerView = view.findViewById(R.id.HomeRecyclerView)
            recyclerAdapter = mealAdapter(meals,requireActivity(), this)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)



        }





    }

    override fun onClick(model: MealX) {
        Toast.makeText(requireActivity(),"Meal Clicked", Toast.LENGTH_SHORT).show()

    }

    override fun onFav(box: CheckBox) {

        box.setOnCheckedChangeListener { box , isChecked ->
            if (isChecked)
            {
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }


}