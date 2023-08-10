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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.utils.CurrentUser
import com.example.recipe_app.utils.GreenSnackBar
import com.example.recipe_app.utils.GreenSnackBar.showSnackBarLong
import com.example.recipe_app.viewModels.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment()  {
    private lateinit var mealImage : ImageView
    private lateinit var mealName : TextView
    private lateinit var mealDescription :ExpandableTextView
    lateinit var id :String
    private lateinit var textToggle :TextView
    private lateinit var mealArea :TextView
    private lateinit var mealCategory :TextView
    private lateinit var favState :CheckBox
    var videoId : String = ""
    private lateinit var  youtubeVideo : YouTubePlayerView
    private lateinit var btnDisplayBottomSheet : Button
    private val args : DetailsFragmentArgs by navArgs()
    private val detailsViewModel : DetailsViewModel by viewModels()

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
        mealArea = view.findViewById(R.id.meal_area)
        mealCategory = view.findViewById(R.id.meal_category)
        btnDisplayBottomSheet = view.findViewById(R.id.btnBottomSheet)
        favState = view.findViewById(R.id.fav_box_v3)


        mealName.text = args.meal.strMeal
        mealDescription.text = args.meal.strInstructions
        Glide.with(this).load(args.meal.strMealThumb).into(mealImage)
        videoId = args.meal.strYoutube!!
        mealArea.text = args.meal.strArea
        mealCategory.text = args.meal.strCategory
        favState.isChecked = args.meal.isFavourite


        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        btnDisplayBottomSheet.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val btnDismissBottomSheet: Button = bottomSheetView.findViewById(R.id.btnDismiss)


            btnDismissBottomSheet.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            if (bottomSheetView.parent != null) {
                (bottomSheetView.parent as ViewGroup).removeView(bottomSheetView)
            }
            dialog.setContentView(bottomSheetView)
            dialog.show()
        }


        youtubeVideo = bottomSheetView.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubeVideo)
        youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {


                val result = videoId.substringAfter("v=")
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

            val userid=CurrentUser.getCurrentUser(requireActivity())
            if (isChecked)
            {
                detailsViewModel.insertFavMealToUser(args.meal,userid)
                showSnackBarLong(view,"Added to favourites")

            }
            else
            {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Do you want to delete the item ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes"){ _, _ ->
                        detailsViewModel.deleteFavMealById(args.meal.idMeal,userid)
                        showSnackBarLong(view,"Removed from favourites")
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


}