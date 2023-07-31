package com.example.recipe_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(){
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var  bottomNavigationView : BottomNavigationView
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTrans: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigationView= findViewById(R.id.bottomNavigationView)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController




        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeItem -> {

                    navController.navigate(R.id.homeFragment)

                    true
                }
                R.id.searchItem -> {

                    navController.navigate(R.id.searchFragment)

                    true
                }
                R.id.favItem -> {

                    navController.navigate(R.id.favouriteFragment)

                    true
                }
            }
            true
        }




    }




}
