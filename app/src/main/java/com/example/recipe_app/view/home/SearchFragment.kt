package com.example.recipe_app.view.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX
import com.example.recipe_app.viewModels.DetailsViewModel
import com.example.recipe_app.viewModels.SearchViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() , OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var detailsViewModel: DetailsViewModel
    val searchViewModel: SearchViewModel by viewModels()
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: searchAdapter
    lateinit var text_NO_MEALS: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        searchViewModel.getFavMealsByUserId(userid!!)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.searchRecyclerView)
        text_NO_MEALS = view.findViewById(R.id.noMaTCHEsFoundTextView)
        recyclerAdapter = searchAdapter(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        searchViewModel.favMeal.observe(viewLifecycleOwner){favMeals->
            searchViewModel.listOfMeals.observe(viewLifecycleOwner){meals->
                var ApiMeal = meals
                ApiMeal.forEach { meal ->
                    favMeals.forEach { favMeal ->
                        if (meal.idMeal == favMeal.idMeal) {
                            meal.isFavourite = true
                        }
                    }
                }

                if (ApiMeal.isNullOrEmpty()){
                    //todo replace the text view below with animation
                    text_NO_MEALS.visibility = View.VISIBLE
                }else{
                    //todo replace the text view below with animation
                    text_NO_MEALS.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    recyclerAdapter.setDataAdapter(ApiMeal)

                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getMeals(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.getMeals(newText!!)
                return false
            }

        })
    }

    override fun onClick(model: MealX) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(model.idMeal))
    }

    override fun onFav(isChecked: Boolean, meal: MealX) {
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        if (isChecked)
        {
            searchViewModel.insertFavMealToUser(meal,userid!!)
            recyclerAdapter.updateItem(isChecked,meal)
            Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to delete the item ?")
                .setCancelable(true)
                .setPositiveButton("Yes"){dialog , it ->
                    recyclerAdapter.updateItem(false,meal)
                    searchViewModel.deleteFavMealById(meal.idMeal,userid!!)
                    Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No"){dialog , it ->
                    dialog.cancel()
                    recyclerAdapter.updateItem(true,meal)
                    recyclerAdapter.notifyDataSetChanged()
                }
            val dialog = builder.create()
            dialog.show()
        }

    }



}




