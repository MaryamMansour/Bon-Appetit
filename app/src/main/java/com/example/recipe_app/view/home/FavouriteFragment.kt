package com.example.recipe_app.view.home

import android.app.AlertDialog
import android.content.Context
//import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.MealX
import com.example.recipe_app.model.UserFavourite
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.FavouriteViewModel
import com.example.recipe_app.viewModels.FavouriteViewModelFactory



class FavouriteFragment : Fragment(), OnClickListener {
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var favouriteViewModel: FavouriteViewModel
    lateinit var favRecyclerView: RecyclerView
    lateinit var favRecyclerAdapter : favMealAdapter
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favRecyclerView = view.findViewById(R.id.FavRecyclerView)
        getViewModelReady()

        var pref=requireActivity().getSharedPreferences("mypref",0)
        var userid=pref.getString("CurrentUserMail","0")
        favouriteViewModel.getFavMealsByUserId(userid!!)

        favRecyclerAdapter = favMealAdapter(this)
        favRecyclerView.adapter = favRecyclerAdapter
        favRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        favouriteViewModel.favMeal.observe(viewLifecycleOwner){
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
                                    //todo delete from database and refresh the recycler view
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
       //todo navigate and  pass id of meal to the details fragment
    }

    override fun onFav(isChecked: Boolean, meal: MealX) {
        //do nothing
    }

    private fun getViewModelReady() {
        val favouriteViewModelFactory = FavouriteViewModelFactory(RepositoryImpl(LocalSourceImp(requireActivity()), ApiClient))
        favouriteViewModel= ViewModelProvider(requireActivity(),favouriteViewModelFactory).get(FavouriteViewModel::class.java)
    }



}