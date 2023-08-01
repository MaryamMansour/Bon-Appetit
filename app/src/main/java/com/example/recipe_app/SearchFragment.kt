package com.example.recipe_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.model.MealX
import java.util.Locale


class SearchFragment : Fragment() ,OnClickListener {
    lateinit var viewModel: HomeMealsViewModel
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

        viewModel = ViewModelProvider(this).get(HomeMealsViewModel::class.java)
        viewModel.getMeals()

        // var recyclerAdapter : mealAdapter

        searchView = view.findViewById(R.id.searchView)
        viewModel.listOfMeals.observe(this) { meals ->

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

//            private fun filterList(query: String?) {
//
//                if (query != null) {
//                    val filteredList : MutableList<MealX> = ArrayList()
//                    for (i in mList) {
//                        if (i.strMeal.lowercase(Locale.ROOT).contains(query)) {
//                            filteredList.add(i)
//                        }
//                    }
//
//                    if (filteredList.isEmpty()) {
//                        Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT)
//                            .show()
//                        recyclerAdapter.setFilteredList(filteredList)
//                    } else {
//                        recyclerAdapter.setFilteredList(filteredList)
//                    }
//                }
//            }






    override fun onClick(model: MealX) {
        Toast.makeText(requireActivity(),"Meal Clicked", Toast.LENGTH_SHORT).show()

    }


}