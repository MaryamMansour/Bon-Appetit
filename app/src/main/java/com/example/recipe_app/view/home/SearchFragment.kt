package com.example.recipe_app.view.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.DetailsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout


class SearchFragment : Fragment() , OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var detailsViewModel: DetailsViewModel
    lateinit var HomeViewModel: HomeMealsViewModel
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: searchAdapter
    lateinit var shimmer: ShimmerFrameLayout
    lateinit var text_NO_MEALS: TextView
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
        getViewModelReady()
        searchView = view.findViewById(R.id.searchView)
        shimmer = view.findViewById(R.id.shimmerFrameLayout_search)
        recyclerView = view.findViewById(R.id.searchRecyclerView)
        text_NO_MEALS = view.findViewById(R.id.noMaTCHEsFoundTextView)
        recyclerAdapter = searchAdapter(listOf(), requireActivity(), this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                HomeViewModel.getSearchedMeals(query!!)
                recyclerAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                HomeViewModel.getSearchedMeals(newText!!)
                recyclerAdapter.notifyDataSetChanged()
                return false
            }

        })
        HomeViewModel.listOfMealsSearch.observe(viewLifecycleOwner) { meals ->
            if (meals.isNotEmpty()) {
                text_NO_MEALS.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            } else if (searchView.query.isNotEmpty()) {
                text_NO_MEALS.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            recyclerAdapter.setDataAdapter(meals)
        }

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

    }


    override fun onClick(model: MealX) {
        //Toast.makeText(requireActivity(), "Meal Clicked", Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.detailsFragment, bundleOf(
            HomeFragment.ARGS to model.strMeal , HomeFragment.ARGS2 to model.strInstructions,
            HomeFragment.ARGS3 to model.strMealThumb)
        )
    }




    override fun onFav(isChecked: Boolean, meal: MealX) {
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        if (isChecked)
        {
            HomeViewModel.inserFavtMeal(UserFavourite(userid!! ,meal.idMeal))
            HomeViewModel.insertFavMealItem(meal)
            Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()

        }
        else
        {
            HomeViewModel.deleteFavMeal(userid!!,meal.idMeal)
            Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()

        }

    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()),ApiClient)
        )

        HomeViewModel= ViewModelProvider(requireActivity(),mealsFactory).get(HomeMealsViewModel::class.java)
    }

}




