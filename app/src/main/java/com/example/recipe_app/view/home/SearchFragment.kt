package com.example.recipe_app.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory


class SearchFragment : Fragment() , OnClickListener {
    lateinit var HomeViewModel: HomeMealsViewModel
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: searchAdapter
    private var mList = ArrayList<MealX>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       activity?.title = "Search"

//        viewModel = ViewModelProvider(this).get(HomeMealsViewModel::class.java)
        getViewModelReady()
        HomeViewModel.getMeals()

        // var recyclerAdapter : mealAdapter

        searchView = view.findViewById(R.id.searchView)
        HomeViewModel.listOfMeals.observe(viewLifecycleOwner) { meals ->

            recyclerView = view.findViewById(R.id.searchRecyclerView)
            recyclerAdapter = searchAdapter(meals, requireActivity(), this)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerAdapter.filter.filter(query)
                recyclerAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerAdapter.filter.filter(newText)
                recyclerAdapter.notifyDataSetChanged()
                return false
            }

        })
    }

    }


    override fun onClick(model: MealX) {
        Toast.makeText(requireActivity(),"Meal Clicked", Toast.LENGTH_SHORT).show()

    }


    override fun onFav(isChecked: Boolean, meal: MealX) {

            if (isChecked)
            {
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
                HomeViewModel.insertMeal(meal)
            }
            else
            {
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
                HomeViewModel.deleteFavMeal(meal)
            }

    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()),ApiClient)
        )

        HomeViewModel= ViewModelProvider(this,mealsFactory).get(HomeMealsViewModel::class.java)
    }

}