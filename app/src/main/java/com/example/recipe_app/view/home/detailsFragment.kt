package com.example.recipe_app.view.home

import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS2
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS3
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS4
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS5
import com.example.recipe_app.view.home.HomeFragment.Companion.ARGS6
import com.example.recipe_app.viewModels.DetailsViewModel
import com.example.recipe_app.viewModels.DetailsViewModelFactory
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class detailsFragment : Fragment() ,OnClickListener {
    lateinit var HomeViewModel: HomeMealsViewModel
    lateinit var detailsViewModel: DetailsViewModel
    lateinit var mealImage : ImageView
    lateinit var mealName : TextView
    lateinit var mealDescription :ExpandableTextView
    lateinit var id :String
    lateinit var textToggle :TextView
    lateinit var meal_area :TextView
    lateinit var meal_category :TextView
//    lateinit var videoView :VideoView
    var videoId : String = ""
lateinit var  youtubeVideo : YouTubePlayerView
lateinit var btnDisplayBottomSheet : Button
    //lateinit var btnDismissBottomSheet : Button

   // @SuppressLint("SetJavaScriptEnabled")

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
        meal_area =view.findViewById(R.id.meal_area)
        meal_category =view.findViewById(R.id.meal_category)
        btnDisplayBottomSheet =view.findViewById(R.id.btnBottomSheet)

        val bottom_sheet_view = layoutInflater.inflate(R.layout.bottom_sheet,null)
        btnDisplayBottomSheet.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            var btnDismissBottomSheet : Button =bottom_sheet_view.findViewById(R.id.btnDismiss)



            btnDismissBottomSheet.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            if (bottom_sheet_view.getParent() != null) {
                (bottom_sheet_view.getParent() as ViewGroup).removeView(bottom_sheet_view)
            }


            dialog.setContentView(bottom_sheet_view)
            dialog.show()
        }


       youtubeVideo =bottom_sheet_view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubeVideo)
        youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {


                val result = videoId.substringAfter("v=")
                youTubePlayer.loadVideo(result, 0f)
            }
        })



        textToggle=view.findViewById(R.id.textView8)
        mealDescription.setAnimationDuration(750L)
        mealDescription.setInterpolator(OvershootInterpolator())
        textToggle.setOnClickListener {
            if(mealDescription.isExpanded){
                mealDescription.collapse()
                textToggle.setText(R.string.see_more)
            } else{
                mealDescription.expand()
                textToggle.setText(R.string.see_less)
            }
        }

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
        videoId = arguments?.getString(ARGS4).toString()
        meal_area.text =arguments?.getString(ARGS5).toString()
        meal_category.text=arguments?.getString(ARGS6).toString()

    }

    private fun getViewModelReady() {
        val mealsFactory = DetailsViewModelFactory(
            RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient)
        )

        detailsViewModel= ViewModelProvider(this,mealsFactory).get(DetailsViewModel::class.java)
    }

    override fun onClick(model: MealX) {
        TODO("Not yet implemented")
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


}