package com.example.recipe_app.view.home

import android.app.AlertDialog
//import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.recipe_app.utils.NetworkUtils.isInternetAvailable
import com.example.recipe_app.viewModels.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    val favouriteViewModel: FavouriteViewModel by viewModels()
    lateinit var favRecyclerView: RecyclerView
    lateinit var favRecyclerAdapter : favMealAdapter
    private lateinit var builder: AlertDialog.Builder
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

        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        if(isInternetAvailable(requireActivity())) {
            favouriteViewModel.getFavMealsByUserId(userid!!)
        }
        else{
            Toast.makeText(requireActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
        favRecyclerAdapter = favMealAdapter(this)
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
                                .setPositiveButton("Yes"){dialog , it ->
                                    favouriteViewModel.deleteFavMealById(favRecyclerAdapter.listOfMeals[viewHolder.adapterPosition].idMeal,userid!!)
                                    favRecyclerAdapter.deleteItem(viewHolder.adapterPosition)
                                    if(favRecyclerAdapter.listOfMeals.isNullOrEmpty()){
                                        lottieEmpty.visibility=View.VISIBLE
                                    }

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

    }


    override fun onClick(model: MealX) {
       findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(model))
    }

    override fun onFav(isChecked: Boolean, meal: MealX) {
        //do nothing
    }





}