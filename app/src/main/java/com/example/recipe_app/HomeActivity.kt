package com.example.recipe_app

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
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
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"

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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_signout ->{
                navController.navigate(R.id.signoutFragment)
                }
            R.id.action_about -> {
            navController.navigate(R.id.aboutFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }



}
