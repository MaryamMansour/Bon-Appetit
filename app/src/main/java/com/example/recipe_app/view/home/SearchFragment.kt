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
import com.example.recipe_app.viewModels.SearchViewModel
import com.example.recipe_app.viewModels.SearchViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout


class SearchFragment : Fragment() , OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var detailsViewModel: DetailsViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: searchAdapter
    lateinit var shimmer: ShimmerFrameLayout
    lateinit var text_NO_MEALS: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()

        searchView = view.findViewById(R.id.searchView)
        shimmer = view.findViewById(R.id.shimmerFrameLayout_search)
        recyclerView = view.findViewById(R.id.searchRecyclerView)
        text_NO_MEALS = view.findViewById(R.id.noMaTCHEsFoundTextView)
        recyclerAdapter = searchAdapter(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        //todo observe the data from the view model

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //todo call the view model function to get the data
                //todo set the data to the adapter
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //todo call the view model function to get the data
                //todo set the data to the adapter
                return false
            }

        })
        //todo observe the data from the view model
    }

    override fun onClick(model: UserFavourite) {
        //todo navigate to the details fragment with the model id
    }

    override fun onFav(isChecked: Boolean, meal: UserFavourite) {
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        if (isChecked)
        {
            //todo call the view model function to add the meal to the favourites
            Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()

        }
        else
        {
            //todo call the view model function to remove the meal from the favourites
            Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()

        }

    }

    private fun getViewModelReady() {
        val searchFactory = SearchViewModelFactory(RepositoryImpl(LocalSourceImp(requireActivity()),ApiClient))
        searchViewModel= ViewModelProvider(requireActivity(),searchFactory).get(SearchViewModel::class.java)
    }

}




