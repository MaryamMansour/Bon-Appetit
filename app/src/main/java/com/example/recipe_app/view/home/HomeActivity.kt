package com.example.recipe_app.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.view.auth.MainActivity
import com.example.recipe_app.viewModels.HomeMealsViewModel
import com.example.recipe_app.viewModels.HomeMealsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(){
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var  bottomNavigationView : BottomNavigationView
    lateinit var toolbar: Toolbar
    lateinit var HomeViewModel: HomeMealsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mealsFactory = HomeMealsViewModelFactory(
            RepositoryImpl(LocalSourceImp(this), ApiClient)
        )

        HomeViewModel = ViewModelProvider(this, mealsFactory).get(HomeMealsViewModel::class.java)

        var pref=getSharedPreferences("mypref", MODE_PRIVATE)
        var userId=pref.getString("CurrentUserMail","")
        HomeViewModel.getMeals()
        HomeViewModel.getRandomMeal()
        HomeViewModel.getAllFavMeals(userId!!)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        setupActionBarWithNavController(navController,appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.inflateMenu(R.menu.home_menu)
        toolbar.setOnMenuItemClickListener {
            if (it.getItemId() == R.id.action_signout) {
                var pref = getSharedPreferences("mypref", MODE_PRIVATE)
                var editor = pref.edit()
                editor.putBoolean("isloggedin", false)
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            } else if (it.getItemId() == R.id.action_about) {
                navController.navigate(R.id.aboutFragment)
                true
            } else {
                false

            }
        }

        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.detailsFragment -> {
                    bottomNavigationView.visibility = BottomNavigationView.GONE
                }
                R.id.aboutFragment -> {
                    bottomNavigationView.visibility = BottomNavigationView.GONE
                }
                else -> {
                    bottomNavigationView.visibility = BottomNavigationView.VISIBLE
                }

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

}
