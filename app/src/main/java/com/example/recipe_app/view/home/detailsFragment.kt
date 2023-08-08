package com.example.recipe_app.view.home

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX
import com.example.recipe_app.viewModels.DetailsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class detailsFragment : Fragment()  {
    lateinit var mealImage : ImageView
    lateinit var mealName : TextView
    lateinit var mealDescription :ExpandableTextView
    lateinit var id :String
    lateinit var textToggle :TextView
    lateinit var meal_area :TextView
    lateinit var meal_category :TextView
    lateinit var favState :CheckBox
    var videoId : String = ""
    lateinit var  youtubeVideo : YouTubePlayerView
    lateinit var btnDisplayBottomSheet : Button
    val args : detailsFragmentArgs by navArgs()
    val detailsViewModel : DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mealImage = view.findViewById(R.id.imageView2)
        mealName = view.findViewById(R.id.textView2)
        mealDescription = view.findViewById(R.id.textView3)
        meal_area = view.findViewById(R.id.meal_area)
        meal_category = view.findViewById(R.id.meal_category)
        btnDisplayBottomSheet = view.findViewById(R.id.btnBottomSheet)
        favState = view.findViewById(R.id.fav_box_v3)



        mealName.text = args.meal.strMeal
        mealDescription.text = args.meal.strInstructions
        Glide.with(this).load(args.meal.strMealThumb).into(mealImage)
        videoId = args.meal.strYoutube!!
        meal_area.text = args.meal.strArea
        meal_category.text = args.meal.strCategory
        favState.isChecked = args.meal.isFavourite


        val bottom_sheet_view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        btnDisplayBottomSheet.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            var btnDismissBottomSheet: Button = bottom_sheet_view.findViewById(R.id.btnDismiss)



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


        youtubeVideo = bottom_sheet_view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubeVideo)
        youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {


                val result = videoId.substringAfter("v=")
                //youTubePlayer.loadVideo(result,false)
                youTubePlayer.cueVideo(result, 0f)

            }
        })



        textToggle = view.findViewById(R.id.textView8)
        mealDescription.setAnimationDuration(750L)
        mealDescription.setInterpolator(OvershootInterpolator())
        textToggle.setOnClickListener {
            if (mealDescription.isExpanded) {
                mealDescription.collapse()
                textToggle.setText(R.string.see_more)
            } else {
                mealDescription.expand()
                textToggle.setText(R.string.see_less)
            }
        }


        favState.setOnCheckedChangeListener { buttonView, isChecked ->

            var pref=requireActivity().getSharedPreferences("mypref",0)
            var userid=pref.getString("CurrentUserMail","0")
            if (isChecked)
            {
                detailsViewModel.insertFavMealToUser(args.meal,userid!!)
                Toast.makeText(requireActivity(),"Added to favourites", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Do you want to delete the item ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes"){dialog , it ->
                        detailsViewModel.deleteFavMealById(args.meal.idMeal,userid!!)
                        Toast.makeText(requireActivity(),"Removed from favourites", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No"){dialog , it ->
                        dialog.cancel()
                        buttonView.isChecked=true
                    }
                val dialog = builder.create()
                dialog.show()
            }
        }



    }




    ///////


    //////


}