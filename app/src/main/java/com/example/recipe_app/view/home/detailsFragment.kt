package com.example.recipe_app.view.home

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS2
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS3
import com.example.recipe_app.viewModels.DetailsViewModel
import com.example.recipe_app.viewModels.DetailsViewModelFactory
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory


class detailsFragment : Fragment() {

    lateinit var detailsViewModel: DetailsViewModel
    lateinit var mealImage : ImageView
    lateinit var mealName : TextView
    lateinit var mealDescription :TextView
    lateinit var id :String
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealImage = view.findViewById(R.id.imageView2)
        mealName =view.findViewById(R.id.textView2)
        mealDescription =view.findViewById(R.id.textView3)


        getViewModelReady()
       detailsViewModel.getDetails()
        detailsViewModel.detailsMeal?.observe(viewLifecycleOwner){
            displayinfo(it)
        }
    }

    private fun displayinfo(it: List<MealX>?) {
        mealName.text =arguments?.getString(ARGS).toString()
        mealDescription.text =arguments?.getString(ARGS2).toString()
        Glide.with(this).load(arguments?.getString(ARGS3).toString()).into(mealImage)
    }

    private fun getViewModelReady() {
        val mealsFactory = DetailsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient)
        )

        detailsViewModel= ViewModelProvider(this,mealsFactory).get(DetailsViewModel::class.java)
    }
}