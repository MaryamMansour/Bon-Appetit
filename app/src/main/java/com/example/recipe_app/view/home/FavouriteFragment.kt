package com.example.recipe_app.view.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.recipe_app.R
import com.example.recipe_app.model.MealX
import com.example.recipe_app.utils.CurrentUser
import com.example.recipe_app.utils.GreenSnackBar
import com.example.recipe_app.utils.GreenSnackBar.showSnackBarLong
import com.example.recipe_app.utils.GreenSnackBar.showSnackBarWithDismiss
import com.example.recipe_app.utils.NetworkUtils.isInternetAvailable
import com.example.recipe_app.viewModels.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), OnClickListener {

    val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var favRecyclerView: RecyclerView
    lateinit var favRecyclerAdapter : FavMealAdapter
    lateinit var lottieEmpty :LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favRecyclerView = view.findViewById(R.id.FavRecyclerView)
        lottieEmpty=view.findViewById(R.id.txtBackFav)

        val userid=CurrentUser.getCurrentUser(requireActivity())
        if(isInternetAvailable(requireActivity())) {
            favouriteViewModel.getFavMealsByUserId(userid)
        }
        else{
            showSnackBarWithDismiss(view,"No Internet Connection")
            }
        favRecyclerAdapter = FavMealAdapter(this)
        favRecyclerView.adapter = favRecyclerAdapter
        favRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        favouriteViewModel.favMeal.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                lottieEmpty.visibility=View.VISIBLE
            }
            else{
                lottieEmpty.visibility=View.GONE
            }
            favRecyclerAdapter.setDataAdapter(it)

        }
            val slideGesture = object : SlideGesture(requireContext()){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when(direction)
                    {
                        ItemTouchHelper.LEFT -> {



                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("Do you want to delete the item ?")
                                .setCancelable(true)
                                .setPositiveButton("Yes"){ _, _ ->
                                    favouriteViewModel.deleteFavMealById(favRecyclerAdapter.listOfMeals[viewHolder.adapterPosition].idMeal,userid!!)
                                    favRecyclerAdapter.deleteItem(viewHolder.adapterPosition)
                                    showSnackBarLong(view,"Item Deleted")
                                    if(favRecyclerAdapter.listOfMeals.isEmpty()){
                                        lottieEmpty.visibility=View.VISIBLE
                                    }

                                }

                                .setNegativeButton("No"){ dialog, _ ->
                                    dialog.cancel()
                                    favRecyclerAdapter.notifyItemChanged(viewHolder.adapterPosition)
                                }
                                .setCancelable(false)
                            val dialog = builder.create()
                            dialog.show()
                        }

                    }
                }

            }
            val touchHelper= ItemTouchHelper(slideGesture)
            touchHelper.attachToRecyclerView(favRecyclerView)

    }


    override fun onClick(model: MealX) {
       findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(model))
    }

    override fun onFav(isChecked: Boolean, meal: MealX) {
        //do nothing
    }





}