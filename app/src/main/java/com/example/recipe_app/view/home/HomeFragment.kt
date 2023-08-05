package com.example.recipe_app.view.home

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.model.MealX
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout


class HomeFragment : Fragment(), OnClickListener {

    lateinit var HomeViewModel: HomeMealsViewModel

    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment

    lateinit var recyclerView: RecyclerView
    lateinit var favouriteBox: CheckBox
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
        // Inflate the layout for this fragment
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

        navHostFragment =requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController


        getViewModelReady()
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        HomeViewModel.getMeals()
        HomeViewModel.getFavMeals(userid!!)
        HomeViewModel.getRandomMeal()


        HomeViewModel.listOfMeals.observe(viewLifecycleOwner){ meals->
            var listofMeals= meals
            listofMeals.forEach {
                HomeViewModel.listOfFavMeals.observe(viewLifecycleOwner){ favMeals->
                    favMeals.forEach { favMeal->
                        if (it.idMeal == favMeal.mealId)
                        {
                            it.fav = true
                        }
                    }
                }
            }
            recyclerView = view.findViewById(R.id.HomeRecyclerView)
            recyclerAdapter = home_adapter(this).apply {
                setDataToAdapter(listofMeals)
            }


            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2,GridLayoutManager.HORIZONTAL, false)
//            recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)

            HomeViewModel.randomMeal.observe(viewLifecycleOwner){ randomMeal->
                nameRandom.text = randomMeal.strMeal
                catRandom.text = randomMeal.strCategory .plus(" |")
                areaRandom.text = randomMeal.strArea
                Glide.with(this)
                    .load(randomMeal.strMealThumb)
                    .into(imgRandom)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                constrainRandom.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
                constrainRandom.setOnClickListener {
                   // Toast.makeText(requireActivity()," Random Meal Clicked", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.detailsFragment, bundleOf(ARGS to randomMeal.strMeal
                        ,ARGS2 to randomMeal.strInstructions,
                        ARGS3 to randomMeal.strMealThumb))
                }
            }

        }

    }

    override fun onClick(model: MealX) {
      navController.navigate(R.id.detailsFragment, bundleOf(ARGS to model.strMeal ,ARGS2 to model.strInstructions,
          ARGS3 to model.strMealThumb))
    }
    companion object{
        var ARGS = HomeFragment::class.java.simpleName + "Details"
        var ARGS2 = HomeFragment::class.java.simpleName + "Details2"
        var ARGS3 = HomeFragment::class.java.simpleName + "Details3"
    }
    override fun onFav(isChecked: Boolean, meal: MealX) {
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        Log.d("zarea in onfav home",userid!!)
            if (isChecked)
            {
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()

                HomeViewModel.inserFavtMeal(UserFavourite(userid!!,meal.idMeal))
                HomeViewModel.insertFavMealItem(meal)
                Log.d("zarea in checked_true home",meal.idMeal+" "+userid)
                HomeViewModel.getMeals()
                HomeViewModel.getFavMeals(userid!!)
            }
            else
            {
                Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
                HomeViewModel.deleteFavMeal(userid!!,meal.idMeal)
                Log.d("zarea in checked_false home",meal.idMeal+" "+userid)
                HomeViewModel.getMeals()
                HomeViewModel.getFavMeals(userid!!)

            }

    }

    private fun getViewModelReady() {
        val mealsFactory = HomeMealsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient)
        )

        HomeViewModel= ViewModelProvider(requireActivity(),mealsFactory).get(HomeMealsViewModel::class.java)
    }


}