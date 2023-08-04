package com.example.recipe_app.view.home

import android.app.AlertDialog
//import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory


class FavouriteFragment : Fragment(), OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var HomeViewModel: HomeMealsViewModel
    lateinit var favRecyclerView: RecyclerView
    lateinit var favRecyclerAdapter : favMealAdapter
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Favourites"
        getViewModelReady()

        HomeViewModel.getFavMeals()

        HomeViewModel.listOfFavMeals.observe(viewLifecycleOwner){ meals->

            favRecyclerView = view.findViewById(R.id.FavRecyclerView)
            favRecyclerAdapter = favMealAdapter(meals,this)

            val slideGesture = object : SlideGesture(requireContext()){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when(direction)
                    {
                        ItemTouchHelper.LEFT -> {



                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("Do you want to delete the item ?")
                                .setCancelable(true)
                                .setPositiveButton("Yes"){dialog , it ->
                                    HomeViewModel.deleteFavMeal(favRecyclerAdapter.mealListM[viewHolder.adapterPosition].idMeal)
                                    favRecyclerAdapter.deleteItem(viewHolder.adapterPosition)


                                }

                                .setNegativeButton("No"){dialog , it ->
                                    dialog.cancel()
                                    favRecyclerAdapter.notifyItemChanged(viewHolder.adapterPosition)
                                }
                            val dialog = builder.create()
                            dialog.show()
                        }

                    }
                }

            }
            val touchHelper= ItemTouchHelper(slideGesture)
            touchHelper.attachToRecyclerView(favRecyclerView)

            favRecyclerView.adapter = favRecyclerAdapter
            favRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        }
        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

    }


    override fun onClick(model: MealX) {
       // Toast.makeText(requireActivity(),"Meal Clicked", Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.detailsFragment, bundleOf(
            HomeFragment.ARGS to model.strMeal , HomeFragment.ARGS2 to model.strInstructions,
            HomeFragment.ARGS3 to model.strMealThumb)
        )
    }




    override fun onFav(isChecked: Boolean, meal: MealX) {

            if (isChecked)
            {
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
                HomeViewModel.deleteFavMeal(meal.idMeal)
            }

    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient)
        )

        HomeViewModel= ViewModelProvider(this,mealsFactory).get(HomeMealsViewModel::class.java)
    }



}