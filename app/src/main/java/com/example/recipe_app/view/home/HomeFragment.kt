package com.example.recipe_app.view.home

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout


class HomeFragment : Fragment(), OnClickListener {

    lateinit var HomeViewModel: HomeMealsViewModel


    lateinit var recyclerView: RecyclerView
    lateinit var nameRandom :TextView
    lateinit var catRandom :TextView
    lateinit var areaRandom :TextView
    lateinit var constrainRandom :ConstraintLayout
    lateinit var imgRandom :ImageView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var recyclerAdapter: home_adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameRandom = view.findViewById(R.id.name_random)
        catRandom = view.findViewById(R.id.cat_random)
        areaRandom = view.findViewById(R.id.area_random)
        constrainRandom = view.findViewById(R.id.constrain_random)
        imgRandom = view.findViewById(R.id.img_random)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout)


        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userId = pref.getString("CurrentUserMail","")

        getViewModelReady()
        HomeViewModel.getRandomMeal()
        HomeViewModel.getMealsWithFavourite(userId!!)

        recyclerView = view.findViewById(R.id.HomeRecyclerView)
        recyclerAdapter = home_adapter(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2,GridLayoutManager.HORIZONTAL, false)

        HomeViewModel.listOfMeals.observe(viewLifecycleOwner) { meals ->
            recyclerAdapter.setDataToAdapter(meals)
        }

            HomeViewModel.randomMeal.observe(viewLifecycleOwner) { randomMeal ->
                nameRandom.text = randomMeal.strMeal
                catRandom.text = randomMeal.strCategory.plus(" |")
                areaRandom.text = randomMeal.strArea
                Glide.with(this)
                    .load(randomMeal.strMealThumb)
                    .into(imgRandom)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                constrainRandom.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            constrainRandom.setOnClickListener {
                    //todo navigate to details fragment with id of random meal
                }





        }







    override fun onClick(model: MealX) {
      //todo navigate to details fragment with id of meal
    }

    override fun onFav(isChecked: Boolean, meal: MealX) {
        var pref = requireActivity().getSharedPreferences("mypref",0)
        var userId = pref.getString("CurrentUserMail","")
            if (isChecked)
            {
                HomeViewModel.insertFavMealToUser(meal,userId!!)
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
            }
            else
            {
                HomeViewModel.deleteFavMealById(meal.idMeal,userId!!)
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
            }

    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient))
        HomeViewModel= ViewModelProvider(this,mealsFactory).get(HomeMealsViewModel::class.java)
    }


}